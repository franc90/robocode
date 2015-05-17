package pl.edu.agh.robocode.learning;

import piqle.environment.IAction;

public enum RobocodeAction implements IAction {

    GO_UP(-10),
    GO_DOWN(5),
    TURN_RIGHT(2),
    TURN_LEFT(2);

    private final int movement;

    RobocodeAction(int movement) {
        this.movement = movement;
    }

    public int getMovement() {
        return movement;
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
}
