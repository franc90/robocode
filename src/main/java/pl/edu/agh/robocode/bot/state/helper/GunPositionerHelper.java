package pl.edu.agh.robocode.bot.state.helper;

import pl.edu.agh.robocode.bot.state.GunPosition;

public class GunPositionerHelper {

    public static GunPosition getGunPosition(double enemyBearing, double myHeading, double gunHeading) {
        double globalEnemyBearing = getGlobalEnemyBearing(enemyBearing, myHeading);

        if (globalEnemyBearing == gunHeading) {
            return new GunPosition();
        }

        double leftMovement = getLeftMovement(globalEnemyBearing, gunHeading);
        GunPosition gunPosition = new GunPosition();

        if (leftMovement <= 180) {
            gunPosition.setAngle(leftMovement);
            gunPosition.setTurnSide(GunPosition.TurnSide.LEFT);
            return gunPosition;
        }

        double rightMovement = 360 - leftMovement;
        gunPosition.setAngle(rightMovement);
        gunPosition.setTurnSide(GunPosition.TurnSide.RIGHT);
        return gunPosition;
    }

    private static double getGlobalEnemyBearing(double enemyBearing, double myHeading) {
        double globalEnemyBearing = enemyBearing + myHeading;

        if (globalEnemyBearing < 0) {
            globalEnemyBearing += 360;
        }

        while (globalEnemyBearing > 360) {
            globalEnemyBearing -= 360;
        }
        return globalEnemyBearing;
    }

    private static double getLeftMovement(double globalEnemyBearing, double gunHeading) {
        double diff = gunHeading - globalEnemyBearing;
        while (diff < 0) {
            diff += 360;
        }

        return diff;
    }
}
