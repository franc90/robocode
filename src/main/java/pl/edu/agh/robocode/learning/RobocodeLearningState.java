package pl.edu.agh.robocode.learning;

import piqle.environment.AbstractState;
import piqle.environment.IEnvironment;
import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.motion.StraightMotion;


class RobocodeLearningState extends AbstractState {

    private final RobocodeState robocodeState;
    private double distanceToWall;

    static RobocodeLearningStateBuilder build() {
        return environment -> state -> new RobocodeLearningState(environment, state);
    }

    private RobocodeLearningState(IEnvironment environment, RobocodeState robocodeState) {
        super(environment);
        this.robocodeState = robocodeState;
        distanceToWall = robocodeState.getWallDistance().getAheadWallDistance();
    }

    double getDistanceToWall() {
        return distanceToWall;
    }

    @Override
    public RobocodeLearningState copy() {
        return new RobocodeLearningState(getEnvironment(), robocodeState);
    }

    @Override
    public int nnCodingSize() {
        return 0; //used only for neural networks
    }

    @Override
    public double[] nnCoding() {
        return new double[0]; //used only for neural networks
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RobocodeLearningState that = (RobocodeLearningState) o;

        return Double.compare(that.distanceToWall, distanceToWall) == 0;

    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(distanceToWall);
        return (int) (temp ^ (temp >>> 32));
    }

    public void makeMove(RobocodeLearningAction action, double displacementValue) {
        distanceToWall = calculateMove(action.getStraightMotion(), displacementValue);
    }

    private double calculateMove(StraightMotion straightMotion, double displacementValue) {
        return StraightMotion.FORWARD == straightMotion ? displacementValue : -displacementValue;
    }

    interface RobocodeLearningStateBuilder {

        WithStateFunction withEnvironment(IEnvironment environment);
    }

    interface WithStateFunction {

        RobocodeLearningState withState(RobocodeState state);
    }

}
