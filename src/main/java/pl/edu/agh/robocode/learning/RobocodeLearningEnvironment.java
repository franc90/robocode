package pl.edu.agh.robocode.learning;

import piqle.environment.ActionList;
import piqle.environment.IState;
import pl.edu.agh.robocode.properties.EnvironmentProperties;

class RobocodeLearningEnvironment extends AbstractTypedEnvironment<RobocodeLearningState, RobocodeLearningAction> {


    private static final double REWARD = 10.0;
    private static final double PUNISHMENT = -10.0;

    private final RobocodeLearningState defaultInitalState;
    private final EnvironmentProperties properties;

    public RobocodeLearningEnvironment(EnvironmentProperties properties) {
        this.defaultInitalState = RobocodeLearningState.builder()
                .withEnvironment(this)
                .withRobocodeState(properties.getInitialState())
                .build();
        this.properties = properties;
    }

    @Override
    protected ActionList getActionsList(RobocodeLearningState state) {
        ActionList actionList = new ActionList(state);
        for (RobocodeLearningAction robocodeLearningAction : RobocodeLearningAction.values()) {
            actionList.add(robocodeLearningAction);
        }
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
        return state.isStateFinal();
    }

    @Override
    public IState defaultInitialState() {
        return defaultInitalState;
    }
}
