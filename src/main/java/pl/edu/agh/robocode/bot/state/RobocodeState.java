package pl.edu.agh.robocode.bot.state;

import pl.edu.agh.robocode.bot.state.distance.WallDistance;

public class RobocodeState {

    private RobotState robotState;

    private WallDistance wallDistance;

    public RobotState getRobotState() {
        return robotState;
    }

    public void setRobotState(RobotState robotState) {
        this.robotState = robotState;
    }

    public WallDistance getWallDistance() {
        return wallDistance;
    }

    public void setWallDistance(WallDistance wallDistance) {
        this.wallDistance = wallDistance;
    }
}
