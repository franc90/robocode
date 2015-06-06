package pl.edu.agh.robocode.learning;

import piqle.environment.IAction;
import pl.edu.agh.robocode.bot.state.distance.CompassDirection;

enum RobocodeLearningAction implements IAction {

    TO_NORTH(CompassDirection.N, 0.0, 1, 0),
    TO_SOUTH(CompassDirection.S, 180.0, -1, 0),
    TO_WEST(CompassDirection.W, 270.0, 0, -1),
    TO_EAST(CompassDirection.E, 90.0, 0, 1);

    private final CompassDirection direction;
    private final double targetHeading;
    private final int yDisplacementSign;
    private final int xDisplacementSign;

    RobocodeLearningAction(CompassDirection direction, double targetHeading, int yDisplacementSign, int xDisplacementSign) {
        this.direction = direction;
        this.targetHeading = targetHeading;
        this.yDisplacementSign = yDisplacementSign;
        this.xDisplacementSign = xDisplacementSign;
    }

    public Object copy() {
        return this;
    }

    public int nnCodingSize() {
        return 0;
    }

    public double[] nnCoding() {
        return new double[0];
    }

    CompassDirection getDirection() {
        return direction;
    }

    double getTargetHeading() {
        return targetHeading;
    }

    int getYDisplacementSign() {
        return yDisplacementSign;
    }

    int getXDisplacementSign() {
        return xDisplacementSign;
    }

    static RobocodeLearningAction forDirection(CompassDirection direction) {
        for(RobocodeLearningAction action : values())
            if(action.direction == direction)
                return action;
        throw new IllegalArgumentException();
    }
}
