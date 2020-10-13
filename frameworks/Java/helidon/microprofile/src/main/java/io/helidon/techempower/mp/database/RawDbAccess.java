package io.helidon.techempower.mp.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import javax.ws.rs.NotFoundException;

import io.helidon.techempower.mp.model.FortuneModel;
import io.helidon.techempower.mp.model.WorldModel;

@ApplicationScoped
@Named("raw")
public class RawDbAccess implements DatabaseAccess {
    private static final String ONE_WORLD = "SELECT id, randomNumber FROM World WHERE id = ?";
    private static final String UPDATE_WORLD = "UPDATE World SET randomNumber = ? WHERE id = ?";
    private static final String FORTUNES = "SELECT id, message FROM Fortune";

    private final DataSource dataSource;

    @Inject
    RawDbAccess(@Named("techempower") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public WorldModel randomWorld() {
        // 10 000 records, from 1
        int row = nextId();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(ONE_WORLD)) {

            // Execute the statement
            stmt.setInt(1, row);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new WorldModel(rs.getInt("id"),
                                          rs.getInt("randomNumber"));
                }
            }

            throw new NotFoundException("Row " + row + " was not found");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to query database", e);
        }
    }

    @Override
    public List<WorldModel> randomWorlds(int queries) {
        List<WorldModel> result = new ArrayList<>(queries);

        // re-use the connection and prepare statement
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(ONE_WORLD)) {
            for (int i = 0; i < queries; i++) {
                int row = nextId();
                // Execute the statement
                stmt.setInt(1, row);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        result.add(new WorldModel(rs.getInt("id"),
                                                  rs.getInt("randomNumber")));
                        continue;
                    }
                }

                throw new NotFoundException("Row " + row + " was not found");
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to query database", e);
        }

        return result;
    }

    @Override
    public List<WorldModel> updateRandomWorlds(int updates) {
        List<WorldModel> result = new ArrayList<>(updates);

        // re-use the connection and prepare statement
        try (Connection conn = dataSource.getConnection()) {
            // select one by one
            try (PreparedStatement stmt = conn.prepareStatement(ONE_WORLD)) {
                for (int i = 0; i < updates; i++) {
                    int row = nextId();
                    // Execute the statement
                    stmt.setInt(1, row);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            WorldModel worldModel = new WorldModel(rs.getInt("id"),
                                                                   rs.getInt("randomNumber"));
                            worldModel.setRandomNumber(nextId());
                            result.add(worldModel);

                            continue;
                        }
                    }

                    throw new NotFoundException("Row " + row + " was not found");
                }
            }

            // update in a batch
            try (PreparedStatement stmt = conn.prepareStatement(UPDATE_WORLD)) {
                for (WorldModel worldModel : result) {
                    stmt.setInt(1, worldModel.getRandomNumber());
                    stmt.setInt(2, worldModel.getId());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to query database", e);
        }

        return result;
    }

    @Override
    public List<FortuneModel> allFortunes() {
        List<FortuneModel> result = new ArrayList<>(100);

        // re-use the connection and prepare statement
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(FORTUNES)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(new FortuneModel(rs.getInt("id"),
                                                rs.getString("message")));
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to query database", e);
        }

        return result;
    }
}
