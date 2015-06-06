package pl.edu.agh.robocode.learning;

import piqle.environment.AbstractState;
import piqle.environment.ActionList;
import piqle.environment.IEnvironment;
import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.bot.state.RobotState;
import pl.edu.agh.robocode.bot.state.distance.CompassDirection;
import pl.edu.agh.robocode.bot.state.distance.NormalizedDistance;
import pl.edu.agh.robocode.bot.state.distance.Wall;
import pl.edu.agh.robocode.bot.state.distance.WallDistance;
import pl.edu.agh.robocode.bot.state.helper.WallDistanceHelper;

import java.util.EnumSet;


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
        CompassDirection newDirection = action.getDirection();
        RobotState oldRobotState = robocodeState.getRobotState();
        double newHeading = action.getTargetHeading();
        double newY = oldRobotState.getY() + displacement * action.getYDisplacementSign();
        double newX = oldRobotState.getX() + displacement * action.getXDisplacementSign();
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



    NormalizedDistance getDistanceToWall() {
        return distanceToWall;
    }

    CompassDirection getRobotDirection() {
        return robotDirection;
    }

    ActionList getAvailableActions() {
        if(isWallToClose()) {
                EnumSet<RobocodeLearningAction> all = EnumSet.allOf(RobocodeLearningAction.class);
                all.remove(RobocodeLearningAction.forDirection(robotDirection));
                return fromEnumSet(all);
        }
        else if(isWallToFar()) {
            RobocodeLearningAction action = RobocodeLearningAction.forDirection(robotDirection);
            ActionList actionList = new ActionList(this);
            actionList.add(action);
            return actionList;
        }
        return fromEnumSet(EnumSet.allOf(RobocodeLearningAction.class));
    }

    private ActionList fromEnumSet(EnumSet<RobocodeLearningAction> set) {
        ActionList actionList = new ActionList(this);
        for (RobocodeLearningAction robocodeLearningAction : set) {
            actionList.add(robocodeLearningAction);
        }
        return actionList;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean isStateFinal() {
        return robocodeState.getRobotState().getEnergy() <= 0.0;
    }

    boolean isWallToClose() {
        return distanceToWall == NormalizedDistance.SMALL;
    }

    boolean isWallToFar() {
        return distanceToWall == NormalizedDistance.BIG;
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
