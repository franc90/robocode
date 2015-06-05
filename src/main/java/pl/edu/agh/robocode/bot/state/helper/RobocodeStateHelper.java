package pl.edu.agh.robocode.bot.state.helper;

import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.bot.state.RobotState;
import pl.edu.agh.robocode.bot.state.distance.CompassDirection;
import robocode.Robot;

public class RobocodeStateHelper {

    private WallDistanceHelper wallDistanceHelper = new WallDistanceHelper();

    public RobocodeState create(Robot robot) {
        RobotState robotState = RobotState
                .builder()
                .fromRobot(robot)
                .build();

        RobocodeState state = new RobocodeState();
        state.setRobotState(robotState);
        state.setWallDistance(wallDistanceHelper.compute(robotState));
        state.setRobotDirection(getRobotDirection(robotState));

        return state;
    }

    private CompassDirection getRobotDirection(RobotState robotState) {
        double angle = robotState.getHeading();

        if (45 <= angle || angle > 315 ) {
            return CompassDirection.N;
        }

        if (135 <= angle) {
            return CompassDirection.E;
        }

        if (225 <= angle) {
            return CompassDirection.S;
        }

        return CompassDirection.W;
    }

}
