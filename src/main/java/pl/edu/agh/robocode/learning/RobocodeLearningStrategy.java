package pl.edu.agh.robocode.learning;

import piqle.agents.LoneAgent;
import piqle.algorithms.QLearningSelector;
import piqle.referees.OnePlayerReferee;
import pl.edu.agh.robocode.bot.strategy.RobocodeStrategy;
import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.learning.action.RobocodeLearningAction;
import pl.edu.agh.robocode.learning.environment.RobocodeLearningEnvironment;
import pl.edu.agh.robocode.learning.environment.RobocodeLearningState;
import pl.edu.agh.robocode.learning.helpers.HeadingHelper;
import pl.edu.agh.robocode.motion.MotionAction;
import pl.edu.agh.robocode.motion.StraightMotion;
import pl.edu.agh.robocode.motion.TurnMotion;
import pl.edu.agh.robocode.motion.TurnSide;
import pl.edu.agh.robocode.properties.EnvironmentProperties;

public class RobocodeLearningStrategy implements RobocodeStrategy {

    private static final int ITERATIONS = 1;
    private final LoneAgent agent;
    private final QLearningSelector selector;
    private final OnePlayerReferee referee;

    RobocodeLearningStrategy(EnvironmentProperties properties, QLearningSelector selector) {
        this.selector = selector;
        this.selector.setEpsilon(0.05);
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
        selector.setEpsilon(selector.getEpsilon()*0.99999);
        RobocodeLearningAction action = (RobocodeLearningAction) selector.bestAction(learningState);
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
        TurnMotion turnMotion = new TurnMotion(angle, turnSide);
        return new MotionAction(straightMotion, turnMotion);
    }

}
