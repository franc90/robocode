package pl.edu.agh.robocode.learning;

import piqle.environment.AbstractState;
import piqle.environment.IEnvironment;
import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.motion.StraightMotion;


class RobocodeLearningState extends AbstractState {

    private final RobocodeState robocodeState;
    private double distanceToWall;

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

        if (Double.compare(that.distanceToWall, distanceToWall) != 0) return false;
        return !(robocodeState != null ? !robocodeState.equals(that.robocodeState) : that.robocodeState != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = robocodeState != null ? robocodeState.hashCode() : 0;
        temp = Double.doubleToLongBits(distanceToWall);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public void makeMove(RobocodeLearningAction action, double displacementValue) {
        distanceToWall = calculateMove(action.getStraightMotion(), displacementValue);
    }

    private double calculateMove(StraightMotion straightMotion, double displacementValue) {
        return StraightMotion.FORWARD == straightMotion ? displacementValue : -displacementValue;
    }


    public static Builder builder() {
        return new Builder();
    }

    public boolean isStateFinal() {
        return robocodeState.getRobotState().getEnergy() <= 0.0;
    }

    public static class Builder {
        private IEnvironment environment; private RobocodeState robocodeState;

        public Builder withEnvironment(IEnvironment environment) {
            this.environment = environment;
            return this;
        }

        public Builder withRobocodeState(RobocodeState robocodeState) {
            this.robocodeState = robocodeState;
            return this;
        }

        public RobocodeLearningState build() {
            return new RobocodeLearningState(environment, robocodeState);
        }
    }

}
