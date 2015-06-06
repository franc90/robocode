package pl.edu.agh.robocode.learning;

import piqle.agents.LoneAgent;
import piqle.algorithms.QLearningSelector;
import piqle.referees.OnePlayerReferee;
import pl.edu.agh.robocode.bot.RobocodeStrategy;
import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.motion.MotionAction;
import pl.edu.agh.robocode.motion.StraightMotion;
import pl.edu.agh.robocode.motion.TurnMotion;
import pl.edu.agh.robocode.motion.TurnSide;
import pl.edu.agh.robocode.properties.EnvironmentProperties;

public class RobocodeLearningStrategy implements RobocodeStrategy {

    private static final int ITERATIONS = 100;
    private final LoneAgent agent;
    private final QLearningSelector algo;
    private final OnePlayerReferee referee;
    private double epsilon=0.5;

    public RobocodeLearningStrategy(EnvironmentProperties properties) {
        algo = new QLearningSelector();
        algo.setGamma(1.0);
        algo.setEpsilon(epsilon);
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
        epsilon *= 0.99999;
        algo.setEpsilon(epsilon);
        System.out.println("BEST ACTION: "+action);
        return  createMotionAction(state, action);
    }

    private MotionAction createMotionAction(RobocodeState state, RobocodeLearningAction action) {
        StraightMotion straightMotion = action.getStraightMotion();
        double angle = action.calculateNewHeading(state.getRobotState().getHeading());
        System.out.println(angle);
        TurnMotion turnMotion = new TurnMotion(angle, TurnSide.LEFT);
        return new MotionAction(straightMotion, turnMotion);
    }

}
