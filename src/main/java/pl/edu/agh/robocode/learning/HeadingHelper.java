package pl.edu.agh.robocode.learning;

import pl.edu.agh.robocode.motion.TurnSide;

class HeadingHelper {

    static double getMinDifference(double actual, double target) {
        double actualMinusTarget = actual - target;
        double targetMinusActual = target - actual;
        return Math.min(Double.compare(actualMinusTarget, 0) < 0 ? actualMinusTarget + 360 : actualMinusTarget,
                        Double.compare(targetMinusActual, 0) < 0 ? targetMinusActual + 360 : targetMinusActual
                        );
    }

    static TurnSide getTurnSide(double actual, double target) {
        if (Double.compare(actual, target) < 0) {
            if (Math.abs(actual - target) < 180)
                return TurnSide.LEFT;
            else return TurnSide.RIGHT;
        } else {
            if (Math.abs(actual - target) < 180)
                return TurnSide.RIGHT;
            else return TurnSide.LEFT;
        }
    }
}
