package pl.edu.agh.robocode.bots;

import pl.edu.agh.robocode.motion.MotionAction;

public interface RobocodeStrategy {

    MotionAction getAction(RobocodeState state);
    void newRound();
}
