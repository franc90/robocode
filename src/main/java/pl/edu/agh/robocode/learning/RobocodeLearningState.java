package pl.edu.agh.robocode.learning;

import piqle.environment.AbstractState;
import piqle.environment.IEnvironment;
import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.bot.state.RobotState;
import pl.edu.agh.robocode.bot.state.distance.CompassDirection;
import pl.edu.agh.robocode.bot.state.distance.NormalizedDistance;
import pl.edu.agh.robocode.bot.state.distance.Wall;
import pl.edu.agh.robocode.bot.state.distance.WallDistance;
import pl.edu.agh.robocode.bot.state.helper.WallDistanceHelper;


class RobocodeLearningState extends AbstractState {

    private final WallDistanceHelper wallDistanceHelper = new WallDistanceHelper();
    private final RobocodeState robocodeState;
    private final NormalizedDistance distanceToWall;
    private final CompassDirection robotDirection;
    private final CompassDirection wallDirection;

    private RobocodeLearningState(IEnvironment environment, RobocodeState robocodeState) {
        super(environment);
        this.robocodeState = robocodeState;
        Wall<NormalizedDistance> wall = robocodeState.getWallDistance().getNormalizedNearestWallDistance();
        distanceToWall = wall.getDistance();
        wallDirection = wall.getDirection();
        robotDirection = robocodeState.getRobotDirection();
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


    RobocodeLearningState makeMove(RobocodeLearningAction action, double displacement) {
        CompassDirection newDirection = action.calculateNewDirection(robocodeState.getRobotDirection());
        RobotState oldRobotState = robocodeState.getRobotState();
        double newHeading = action.calculateNewHeading(oldRobotState.getHeading());
        double normalizedHeading = newHeading % 90;
        double yDisplacement = displacement * Math.cos(normalizedHeading) * signForDirection(newDirection);
        double xDisplacement = displacement * Math.sin(normalizedHeading) * signForHeadingAndDirection(newHeading, newDirection);
        double newY = oldRobotState.getY() + yDisplacement;
        double newX = oldRobotState.getX() + xDisplacement;
        RobotState newRobotState = new RobotState.Builder()
                                                .fromRobotState(oldRobotState)
                                                .withHeading(newHeading)
                                                .withY(newY)
                                                .withX(newX)
                                                .build();
        WallDistance newWallDistance = wallDistanceHelper.compute(newRobotState);
        RobocodeState newRobocodeState = new RobocodeState();
        newRobocodeState.setRobotDirection(newDirection);
        newRobocodeState.setRobotState(newRobotState);
        newRobocodeState.setWallDistance(newWallDistance);
        return new RobocodeLearningState(getEnvironment(), newRobocodeState);
    }


    private double signForDirection(CompassDirection newDirection) {
        return CompassDirection.N == newDirection ? 1.0 : -1.0;
    }

    private double signForHeadingAndDirection(double newHeading, CompassDirection direction) {
        return CompassDirection.N == direction ? signForNorthAndHeading(newHeading) : signForSouthAndHeading(newHeading);
    }

    private double signForNorthAndHeading(double heading) {
        return heading - 180 >= 0 ? -1.0 : 1.0;
    }

    private double signForSouthAndHeading(double heading) {
        return heading - 180 >= 0 ? 1.0 : -1.0;
    }

    NormalizedDistance getDistanceToWall() {
        return distanceToWall;
    }

    CompassDirection getRobotDirection() {
        return robotDirection;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean isStateFinal() {
        return robocodeState.getRobotState().getEnergy() <= 0.0;
    }

    public static class Builder {
        private IEnvironment environment;
        private RobocodeState robocodeState;

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
