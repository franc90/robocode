package pl.edu.agh.robocode.learning;

import piqle.environment.ActionList;
import piqle.environment.IState;
import pl.edu.agh.robocode.properties.EnvironmentProperties;

import java.util.stream.Stream;

class RobocodeLearningEnvironment extends AbstractTypedEnvironment<RobocodeLearningState, RobocodeLearningAction> {


    private static final double REWARD = 10.0;
    private static final double PUNISHMENT = -10.0;

    private final RobocodeLearningState defaultInitalState;
    private final EnvironmentProperties properties;

    public RobocodeLearningEnvironment(EnvironmentProperties properties) {
        this.defaultInitalState = RobocodeLearningState.build()
                                        .withEnvironment(this)
                                        .withState(properties.getInitialState());
        this.properties = properties;
    }

    @Override
    protected ActionList getActionsList(RobocodeLearningState state) {
        ActionList actionList = new ActionList(state);
        Stream.of(RobocodeLearningAction.values()).forEach(actionList::add);
        return actionList;
    }

    @Override
    protected RobocodeLearningState nextState(RobocodeLearningState state, RobocodeLearningAction action) {
        RobocodeLearningState newState = state.copy();
        newState.makeMove(action, properties.getDisplacementValue());
        return newState;
    }

    @Override
    protected double calculateReward(RobocodeLearningState oldState, RobocodeLearningState newState, RobocodeLearningAction action) {
        return newState.getDistanceToWall() < oldState.getDistanceToWall() ? REWARD : PUNISHMENT;
    }

    @Override
    protected boolean isStateFinal(RobocodeLearningState state) {
        return state.isFinal();
    }

    @Override
    public IState defaultInitialState() {
        return defaultInitalState;
    }
}
