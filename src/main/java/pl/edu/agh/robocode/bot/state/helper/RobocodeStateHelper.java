package pl.edu.agh.robocode.bot.state.helper;

import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.bot.state.RobotState;
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

        return state;
    }

}
