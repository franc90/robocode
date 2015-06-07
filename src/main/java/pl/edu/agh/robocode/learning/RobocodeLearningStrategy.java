package pl.edu.agh.robocode.learning;

import piqle.agents.LoneAgent;
import piqle.algorithms.QLearningSelector;
import piqle.referees.OnePlayerReferee;
import pl.edu.agh.robocode.bot.strategy.RobocodeStrategy;
import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.motion.MotionAction;
import pl.edu.agh.robocode.motion.StraightMotion;
import pl.edu.agh.robocode.motion.TurnMotion;
import pl.edu.agh.robocode.motion.TurnSide;
import pl.edu.agh.robocode.properties.EnvironmentProperties;

public class RobocodeLearningStrategy implements RobocodeStrategy {

    private static final int ITERATIONS = 50;
    private final LoneAgent agent;
    private final QLearningSelector selector;
    private final OnePlayerReferee referee;
    private double epsilon=0.5;

    RobocodeLearningStrategy(EnvironmentProperties properties, QLearningSelector selector) {
        this.selector = selector;
        this.selector.setGamma(1.0);
        this.selector.setEpsilon(epsilon);
        agent = new LoneAgent(new RobocodeLearningEnvironment(properties), this.selector);
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
        RobocodeLearningAction action = (RobocodeLearningAction) selector.bestAction(learningState);
        epsilon *= 0.99999;
        selector.setEpsilon(epsilon);
        System.out.println("BEST ACTION: "+action);
        return  createMotionAction(state, action);
    }

    QLearningSelector getSelector() {
        return selector;
    }

    private MotionAction createMotionAction(RobocodeState state, RobocodeLearningAction action) {
        StraightMotion straightMotion = StraightMotion.FORWARD;
        double actualHeading = state.getRobotState().getHeading();
        double angle = HeadingHelper.getMinDifference(actualHeading, action.getTargetHeading());
        TurnSide turnSide = HeadingHelper.getTurnSide(actualHeading, action.getTargetHeading());
        System.out.println(actualHeading+"|"+angle+"|"+turnSide);
        TurnMotion turnMotion = new TurnMotion(angle, turnSide);
        return new MotionAction(straightMotion, turnMotion);
    }

}
