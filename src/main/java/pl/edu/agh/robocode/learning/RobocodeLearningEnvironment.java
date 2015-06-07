package pl.edu.agh.robocode.learning;

import piqle.environment.ActionList;
import piqle.environment.IState;
import pl.edu.agh.robocode.properties.EnvironmentProperties;

class RobocodeLearningEnvironment extends AbstractTypedEnvironment<RobocodeLearningState, RobocodeLearningAction> {


    private static final double DISTANCE_REWARD = 10.0;
    private static final double DIRECTION_REWARD = 5.0;
    private static final double PUNISHMENT = -15.0;
    private static final double WALL_HIT_PUNISHMENT = -20.0;

    private final RobocodeLearningState defaultInitalState;
    private final double displacement;

    public RobocodeLearningEnvironment(EnvironmentProperties properties) {
        this.defaultInitalState = RobocodeLearningState.builder()
                .withEnvironment(this)
                .withRobocodeState(properties.getInitialState())
                .build();
        this.displacement = properties.getDisplacementValue();
    }

    @Override
    protected ActionList getActionsList(RobocodeLearningState state) {
        return state.getAvailableActions();
    }

    @Override
    protected RobocodeLearningState nextState(RobocodeLearningState state, RobocodeLearningAction action) {
        return state.makeMove(action, displacement);
    }

    @Override
    protected double calculateReward(RobocodeLearningState oldState, RobocodeLearningState newState, RobocodeLearningAction action) {
        return Double.compare(newState.getDistanceToWall(), 0.0) <= 0 ? WALL_HIT_PUNISHMENT : rewardForDistance(oldState, newState);
    }

    private double rewardForDistance(RobocodeLearningState oldState, RobocodeLearningState newState) {
        return newState.getNormalizedDistanceToWall().compareTo(oldState.getNormalizedDistanceToWall()) < 0 ? DISTANCE_REWARD : rewardForDirection(oldState, newState);
    }

    private double rewardForDirection(RobocodeLearningState oldState, RobocodeLearningState newState) {
        return oldState.getRobotDirection() != newState.getRobotDirection() ? DIRECTION_REWARD : PUNISHMENT;
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
