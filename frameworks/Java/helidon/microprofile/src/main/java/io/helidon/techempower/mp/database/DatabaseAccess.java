package io.helidon.techempower.mp.database;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import io.helidon.techempower.mp.model.FortuneModel;
import io.helidon.techempower.mp.model.WorldModel;

/**
 * Abstraction of beans used to access data in a database.
 */
public interface DatabaseAccess {
    /**
     * Random world from database.
     * @return
     */
    WorldModel randomWorld();

    /**
     * A number of random worlds from database.
     * @param queries number of worlds to find
     * @return
     */
    List<WorldModel> randomWorlds(int queries);

    /**
     * Update a number of worlds with a new random number.
     * @param updates number of worlds to update
     * @return
     */
    List<WorldModel> updateRandomWorlds(int updates);

    /**
     * Get all fortunes from database.
     * @return
     */
    List<FortuneModel> allFortunes();

    /**
     * Next random number between 1 (inclusive) and 10000 (inclusive).
     * @return
     */
    default int nextId() {
        return ThreadLocalRandom.current().nextInt(10000) + 1;
    }
}
