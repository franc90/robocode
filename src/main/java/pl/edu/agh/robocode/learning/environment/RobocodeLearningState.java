package pl.edu.agh.robocode.learning.environment;

import piqle.environment.AbstractState;
import piqle.environment.ActionList;
import piqle.environment.IEnvironment;
import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.bot.state.RobotState;
import pl.edu.agh.robocode.bot.state.distance.CompassDirection;
import pl.edu.agh.robocode.bot.state.distance.NormalizedDistance;
import pl.edu.agh.robocode.bot.state.distance.Wall;
import pl.edu.agh.robocode.bot.state.distance.WallDistance;
import pl.edu.agh.robocode.bot.state.enemy.Enemies;
import pl.edu.agh.robocode.bot.state.enemy.Enemy;
import pl.edu.agh.robocode.bot.state.helper.WallDistanceHelper;
import pl.edu.agh.robocode.exception.NullValueException;
import pl.edu.agh.robocode.learning.action.ActionListCreatorFactory;
import pl.edu.agh.robocode.learning.action.ActionSpecification;
import pl.edu.agh.robocode.learning.action.RobocodeLearningAction;


public class RobocodeLearningState extends AbstractState {

    private final transient WallDistanceHelper wallDistanceHelper = new WallDistanceHelper();
    private final transient RobocodeState robocodeState;
    private final transient Enemy enemy;
    private final NormalizedDistance normalizedDistanceToWall;
    private final CompassDirection robotDirection;
    private final CompassDirection wallDirection;
    private final double distanceToWall;
    private final NormalizedDistance normalizedEnemyDistance;
    private final CompassDirection enemyPosition;
    private final double enemyDistance;

    private RobocodeLearningState(IEnvironment environment, RobocodeState robocodeState) {
        super(environment);
        this.robocodeState = robocodeState;
        Wall<NormalizedDistance> wall = robocodeState.getWallDistance().getNormalizedNearestWallDistance();
        normalizedDistanceToWall = wall.getDistance();
        distanceToWall = robocodeState.getWallDistance().getNearestWall().getDistance();
        wallDirection = wall.getDirection();
        robotDirection = robocodeState.getRobotDirection();
        enemy = getEnemy();
        enemyDistance = enemy.getDistance();
        normalizedEnemyDistance = enemy.getNormalizedDistance();
        enemyPosition = enemy.getNormalizedBearing();
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
        double newEnemyDistance = Math.hypot(newX - enemy.getX(), newY - enemy.getY());
        Enemy newEnemy = new Enemy.Builder()
                .fromEnemy(enemy)
                .withDistance(newEnemyDistance)
                .build();
        Enemies enemies = robocodeState.getEnemies();
        enemies.addEnemy(newEnemy.getName(), newEnemy);
        WallDistance newWallDistance = wallDistanceHelper.compute(newRobotState);
        RobocodeState newRobocodeState = new RobocodeState();
        newRobocodeState.setRobotDirection(newDirection);
        newRobocodeState.setRobotState(newRobotState);
        newRobocodeState.setWallDistance(newWallDistance);
        newRobocodeState.setEnemies(enemies);
        return new RobocodeLearningState(getEnvironment(), newRobocodeState);
    }

    NormalizedDistance getNormalizedDistanceToWall() {
        return normalizedDistanceToWall;
    }

    NormalizedDistance getNormalizedEnemyDistance() {
        return normalizedEnemyDistance;
    }

    double getDistanceToWall() {
        return distanceToWall;
    }

    double getDistanceToEnemy() {
        return enemyDistance;
    }

    ActionList getAvailableActions() {
        ActionSpecification specification = new ActionSpecification.ActionSpecificationBuilder()
                                    .withDistanceToEnemy(normalizedEnemyDistance)
                                    .withDistancetToWall(normalizedDistanceToWall)
                                    .withEnemyDirection(enemyPosition)
                                    .withRobotDirection(robotDirection)
                                    .withWallDirection(wallDirection)
                                    .build();
        return ActionListCreatorFactory.forSpecificationAndState(specification, this).create();
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean isStateFinal() {
        return robocodeState.getRobotState().getEnergy() <= 0.0;
    }

    private Enemy getEnemy() {
        try{
            return robocodeState.getEnemies().getNearest();
        } catch (NullValueException e) {
            return new NoEnemyWrapper();
        }
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RobocodeLearningState that = (RobocodeLearningState) o;

        if (normalizedDistanceToWall != that.normalizedDistanceToWall) return false;
        if (robotDirection != that.robotDirection) return false;
        if (wallDirection != that.wallDirection) return false;
        if (normalizedEnemyDistance != that.normalizedEnemyDistance) return false;
        return enemyPosition == that.enemyPosition;

    }

    @Override
    public int hashCode() {
        int result = normalizedDistanceToWall != null ? normalizedDistanceToWall.hashCode() : 0;
        result = 31 * result + (robotDirection != null ? robotDirection.hashCode() : 0);
        result = 31 * result + (wallDirection != null ? wallDirection.hashCode() : 0);
        result = 31 * result + (normalizedEnemyDistance != null ? normalizedEnemyDistance.hashCode() : 0);
        result = 31 * result + (enemyPosition != null ? enemyPosition.hashCode() : 0);
        return result;
    }
}
