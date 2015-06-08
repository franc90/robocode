package pl.edu.agh.robocode.learning.environment;

import pl.edu.agh.robocode.bot.state.distance.CompassDirection;
import pl.edu.agh.robocode.bot.state.distance.NormalizedDistance;
import pl.edu.agh.robocode.bot.state.enemy.Enemy;

class NoEnemyWrapper extends Enemy {

    private static final String NO_ENEMY_CONVENTION_NAME = "_";

    public NoEnemyWrapper() {
        super(NO_ENEMY_CONVENTION_NAME, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0L, 0.0);
    }

    @Override
    public double getDistance() {
        return Double.MAX_VALUE;
    }

    @Override
    public NormalizedDistance getNormalizedDistance() {
        return NormalizedDistance.BIG;
    }

    @Override
    public CompassDirection getNormalizedBearing() {
        return CompassDirection.N;
    }
}
