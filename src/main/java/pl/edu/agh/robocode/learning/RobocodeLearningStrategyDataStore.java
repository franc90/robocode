package pl.edu.agh.robocode.learning;

import piqle.algorithms.QLearningSelector;
import pl.edu.agh.robocode.bot.strategy.RobocodeStrategyDataStore;
import pl.edu.agh.robocode.exception.NullValueException;
import pl.edu.agh.robocode.learning.helpers.PersistingHelper;
import pl.edu.agh.robocode.properties.EnvironmentProperties;

import java.io.File;

public class RobocodeLearningStrategyDataStore implements RobocodeStrategyDataStore<RobocodeLearningStrategy> {

    private final PersistingHelper<QLearningSelector> selectorPersister;

    public RobocodeLearningStrategyDataStore(File robocodeDataFile) {
        selectorPersister = new PersistingHelper<QLearningSelector>(robocodeDataFile);
    }

    public void save(RobocodeLearningStrategy strategy) {
        QLearningSelector selector = strategy.getSelector();
        selectorPersister.persist(selector);
    }

    public RobocodeLearningStrategy load(EnvironmentProperties properties) {
        QLearningSelector selector;
        try {
            selector = selectorPersister.load();
        } catch (NullValueException e) {
            selector = new QLearningSelector();
        }
        return new RobocodeLearningStrategy(properties, selector);
    }
}
