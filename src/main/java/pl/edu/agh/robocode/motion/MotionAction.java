package pl.edu.agh.robocode.motion;

public class MotionAction {

    private final StraightMotion straightMotion;
    private final TurnMotion turnMotion;

    public MotionAction(StraightMotion straightMotion, TurnMotion turnMotion) {
        this.straightMotion = straightMotion;
        this.turnMotion = turnMotion;
    }

    public StraightMotion getStraightMotion() {
        return straightMotion;
    }

    public TurnMotion getTurnMotion() {
        return turnMotion;
    }
}
