package pl.edu.agh.robocode.learning;

import piqle.agents.LoneAgent;
import piqle.algorithms.QLearningSelector;
import piqle.referees.OnePlayerReferee;
import pl.edu.agh.robocode.bot.RobocodeStrategy;
import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.motion.MotionAction;
import pl.edu.agh.robocode.properties.EnvironmentProperties;

public class RobocodeLearningStrategy implements RobocodeStrategy {

    private static final int ITERATIONS = 100;
    private final LoneAgent agent;
    private final QLearningSelector algo;
    private final OnePlayerReferee referee;

    public RobocodeLearningStrategy(EnvironmentProperties properties) {
        algo = new QLearningSelector();
        agent = new LoneAgent(new RobocodeLearningEnvironment(properties), algo);
        agent.enableLearning();
        referee = new OnePlayerReferee(agent);
        referee.setMaxIter(ITERATIONS);
    }

    public MotionAction getAction(RobocodeState state) {
        RobocodeLearningState learningState = RobocodeLearningState.builder()
                .withEnvironment(agent.getEnvironment())
                .withRobocodeState(state)
                .build();
        referee.episode(learningState);
        RobocodeLearningAction action = (RobocodeLearningAction) algo.bestAction(learningState);
        return action.toMotionAction();
    }

}
