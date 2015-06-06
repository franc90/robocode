package pl.edu.agh.robocode.bot.state;

public class GunPosition {

    public enum TurnSide {
        LEFT, RIGHT
    }

    private double angle;
    private TurnSide turnSide;

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public TurnSide getTurnSide() {
        return turnSide;
    }

    public void setTurnSide(TurnSide turnSide) {
        this.turnSide = turnSide;
    }
}
