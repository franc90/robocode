package pl.edu.agh.robocode.motion;

public class TurnMotion {

    private final double angle;
    private final TurnSide turnSide;

    public TurnMotion(double angle, TurnSide turnSide) {
        this.angle = angle;
        this.turnSide = turnSide;
    }

    public double getAngle() {
        return angle;
    }

    public TurnSide getTurnSide() {
        return turnSide;
    }
}
