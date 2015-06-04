package pl.edu.agh.robocode.learning;

import piqle.environment.IAction;
import pl.edu.agh.robocode.motion.MotionAction;
import pl.edu.agh.robocode.motion.StraightMotion;
import pl.edu.agh.robocode.motion.TurnMotion;

enum RobocodeLearningAction implements IAction {

    FORWARD(StraightMotion.FORWARD, TurnMotion.NONE),
    BACKWARD(StraightMotion.BACKWARD, TurnMotion.NONE),
    FORWARD_LEFT(StraightMotion.FORWARD, TurnMotion.LEFT),
    FORWARD_RIGHT(StraightMotion.FORWARD, TurnMotion.RIGHT),
    BACKWARD_LEFT(StraightMotion.BACKWARD, TurnMotion.LEFT),
    BACKWARD_RIGHT(StraightMotion.FORWARD, TurnMotion.RIGHT);

    private final StraightMotion straightMotion;
    private final TurnMotion turnMotion;

    RobocodeLearningAction(StraightMotion straightMotion, TurnMotion turnMotion) {
        this.straightMotion = straightMotion;
        this.turnMotion = turnMotion;
    }

    public StraightMotion getStraightMotion() {
        return straightMotion;
    }

    public TurnMotion getTurnMotion() {
        return turnMotion;
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

    public MotionAction toMotionAction() {
        return new MotionAction(straightMotion, turnMotion);
    }

}
