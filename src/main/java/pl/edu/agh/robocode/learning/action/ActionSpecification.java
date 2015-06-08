package pl.edu.agh.robocode.learning.action;

import pl.edu.agh.robocode.bot.state.distance.CompassDirection;
import pl.edu.agh.robocode.bot.state.distance.NormalizedDistance;

public class ActionSpecification {

    private final CompassDirection robotDirection;
    private final CompassDirection wallDirection;
    private final CompassDirection enemyDirection;
    private final NormalizedDistance distancetToWall;
    private final NormalizedDistance distanceToEnemy;

    ActionSpecification(CompassDirection robotDirection, CompassDirection wallDirection, CompassDirection enemyDirection, NormalizedDistance distancetToWall, NormalizedDistance distanceToEnemy) {
        this.robotDirection = robotDirection;
        this.wallDirection = wallDirection;
        this.enemyDirection = enemyDirection;
        this.distancetToWall = distancetToWall;
        this.distanceToEnemy = distanceToEnemy;
    }

    public CompassDirection getRobotDirection() {
        return robotDirection;
    }

    public CompassDirection getWallDirection() {
        return wallDirection;
    }

    public CompassDirection getEnemyDirection() {
        return enemyDirection;
    }

    public boolean isWallToClose() {
        return isDistanceToClose(distancetToWall);
    }

    public boolean isEnemyToClose() {
        return isDistanceToClose(distanceToEnemy);
    }

    public boolean isWallToFar() {
        return isDistanceToFar(distancetToWall);
    }

    public boolean isEnemyToFar() {
        return isDistanceToFar(distanceToEnemy);
    }

    private boolean isDistanceToFar(NormalizedDistance distance) {
        return distance == NormalizedDistance.BIG;
    }

    private boolean isDistanceToClose(NormalizedDistance distance) {
        return distance == NormalizedDistance.SMALL;
    }

    public static class ActionSpecificationBuilder {
        private CompassDirection robotDirection;
        private CompassDirection wallDirection;
        private CompassDirection enemyDirection;
        private NormalizedDistance distancetToWall;
        private NormalizedDistance distanceToEnemy;

        public ActionSpecificationBuilder withRobotDirection(CompassDirection robotDirection) {
            this.robotDirection = robotDirection;
            return this;
        }

        public ActionSpecificationBuilder withWallDirection(CompassDirection wallDirection) {
            this.wallDirection = wallDirection;
            return this;
        }

        public ActionSpecificationBuilder withEnemyDirection(CompassDirection enemyDirection) {
            this.enemyDirection = enemyDirection;
            return this;
        }

        public ActionSpecificationBuilder withDistancetToWall(NormalizedDistance distancetToWall) {
            this.distancetToWall = distancetToWall;
            return this;
        }

        public ActionSpecificationBuilder withDistanceToEnemy(NormalizedDistance distanceToEnemy) {
            this.distanceToEnemy = distanceToEnemy;
            return this;
        }

        public ActionSpecification build() {
            return new ActionSpecification(robotDirection, wallDirection, enemyDirection, distancetToWall, distanceToEnemy);
        }
    }


}
