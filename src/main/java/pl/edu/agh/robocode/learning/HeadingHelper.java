package pl.edu.agh.robocode.learning;

import pl.edu.agh.robocode.motion.TurnSide;

class HeadingHelper {

    static double getMinDifference(double actual, double target) {
        return Math.min((actual - target) < 0 ? actual - target + 360 : actual - target, (target - actual) < 0 ? target - actual + 360 : target - actual);
    }

    static TurnSide getTurnSide(double actual, double target) {
        if (actual < target) {
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
