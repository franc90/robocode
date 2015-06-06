package pl.edu.agh.robocode.bot.state.distance.helper;

import pl.edu.agh.robocode.bot.state.distance.CompassDirection;

public class CompassDirectionHelper {

    public static final double FULL_CIRCLE = 360.;

    public static CompassDirection normalize(double val) {
        while (val < 0) {
            val += FULL_CIRCLE;
        }
        double normalized = val % FULL_CIRCLE;

        if (-45 <= normalized && normalized < 45) {
            return CompassDirection.N;
        }

        if (45 <= normalized && normalized < 135) {
            return CompassDirection.E;
        }

        if (135 < normalized || normalized < -135) {
            return CompassDirection.S;
        }

        return CompassDirection.W;
    }
}
