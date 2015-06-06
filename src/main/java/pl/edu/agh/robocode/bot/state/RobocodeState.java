package pl.edu.agh.robocode.bot.state;

import pl.edu.agh.robocode.bot.state.distance.CompassDirection;
import pl.edu.agh.robocode.bot.state.distance.WallDistance;
import pl.edu.agh.robocode.bot.state.enemy.Enemies;

public class RobocodeState {

    private RobotState robotState;

    private WallDistance wallDistance;

    private CompassDirection robotDirection;

    private Enemies enemies;

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

    public CompassDirection getRobotDirection() {
        return robotDirection;
    }

    public void setRobotDirection(CompassDirection robotDirection) {
        this.robotDirection = robotDirection;
    }

    public Enemies getEnemies() {
        return enemies;
    }

    public void setEnemies(Enemies enemies) {
        this.enemies = enemies;
    }
}
