package pl.edu.agh.robocode.learning;

import piqle.environment.IAction;
import pl.edu.agh.robocode.bot.state.distance.CompassDirection;
import pl.edu.agh.robocode.motion.StraightMotion;

enum RobocodeLearningAction implements IAction {

    AHEAD(StraightMotion.FORWARD) {
        @Override
        double calculateNewHeading(double oldHeading) {
            return oldHeading;
        }

        @Override
        CompassDirection calculateNewDirection(CompassDirection oldDirection) {
            return oldDirection;
        }
    },

    TURN_BACK(StraightMotion.BACKWARD) {
        @Override
        double calculateNewHeading(double oldHeading) {
            return (oldHeading + 180.0) % 180;
        }

        @Override
        CompassDirection calculateNewDirection(CompassDirection oldDirection) {
            return oldDirection.opposite();
        }
    }


    ;

    private final StraightMotion straightMotion;

    RobocodeLearningAction(StraightMotion straightMotion) {
        this.straightMotion = straightMotion;
    }

    abstract double calculateNewHeading(double oldHeading);

    abstract CompassDirection calculateNewDirection(CompassDirection oldDirection);

    public Object copy() {
        return this;
    }

    public int nnCodingSize() {
        return 0;
    }

    public double[] nnCoding() {
        return new double[0];
    }

    StraightMotion getStraightMotion() {
        return straightMotion;
    }
}
