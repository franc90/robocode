package pl.edu.agh.robocode.learning;

import piqle.agents.LoneAgent;
import piqle.algorithms.QLearningSelector;
import pl.edu.agh.robocode.bot.statistics.Statistics;
import pl.edu.agh.robocode.bot.strategy.RobocodeStrategyDataStore;
import pl.edu.agh.robocode.exception.NullValueException;
import pl.edu.agh.robocode.learning.helpers.PersistingHelper;
import pl.edu.agh.robocode.properties.EnvironmentProperties;

import java.io.File;

public class RobocodeLearningStrategyDataStore implements RobocodeStrategyDataStore<RobocodeLearningStrategy> {

    private final PersistingHelper<QLearningSelector> selectorPersister;
    private final PersistingHelper<LoneAgent> agentPersister;
    private final Statistics rewardsPersister;

    public RobocodeLearningStrategyDataStore(File selectorDataFile, File agentDataFile, File rewardsDataFile) {
        selectorPersister = new PersistingHelper<QLearningSelector>(selectorDataFile);
        agentPersister = new PersistingHelper<LoneAgent>(agentDataFile);
        rewardsPersister = new Statistics(rewardsDataFile);
    }

    public void save(RobocodeLearningStrategy strategy) {
        QLearningSelector selector = strategy.getSelector();
        selectorPersister.persist(selector);
        agentPersister.persist(strategy.getAgent());
        rewardsPersister.save(strategy.reward());
    }

    public RobocodeLearningStrategy load(EnvironmentProperties properties) {
        QLearningSelector selector;
        try {
            selector = selectorPersister.load();
            LoneAgent agent = agentPersister.load();
            System.out.println("Loaded selector");
            return new RobocodeLearningStrategy(selector, agent);
        } catch (NullValueException e) {
            selector = new QLearningSelector();
        }
        return new RobocodeLearningStrategy(properties, selector);
    }
}
