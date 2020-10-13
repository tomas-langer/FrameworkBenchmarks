package io.helidon.techempower.mp.database;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import io.helidon.techempower.mp.model.FortuneModel;
import io.helidon.techempower.mp.model.WorldModel;

@ApplicationScoped
@Named("jpa")
public class JpaAccess implements DatabaseAccess {
    @PersistenceContext(unitName = "techempower")
    private EntityManager entityManager;

    JpaAccess() {
    }

    @Override
    public WorldModel randomWorld() {
        // 10 000 records, from 1
        int row = nextId();
        return entityManager.createNamedQuery("getById", WorldModel.class)
                .setParameter("id", row)
                .getSingleResult();

    }

    @Override
    public List<WorldModel> randomWorlds(int queries) {
        List<WorldModel> result = new ArrayList<>(queries);

        for (int i = 0; i < queries; i++) {
            result.add(randomWorld());
            entityManager.clear();
        }

        return result;
    }

    @Override
    @Transactional
    public List<WorldModel> updateRandomWorlds(int updates) {
        List<WorldModel> result = new ArrayList<>(updates);

        for (int i = 0; i < updates; i++) {
            result.add(selectAndUpdate());
            entityManager.clear();
        }

        return result;
    }

    private WorldModel selectAndUpdate() {
        int randomNumber = nextId();

        WorldModel worldModel = randomWorld();
        worldModel.setRandomNumber(randomNumber);
        entityManager.persist(worldModel);

        return worldModel;
    }

    @Override
    public List<FortuneModel> allFortunes() {
        return entityManager.createNamedQuery("getAll", FortuneModel.class)
                .getResultList();
    }
}
