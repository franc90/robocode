package pl.edu.agh.robocode.bot;

import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.motion.MotionAction;

public interface RobocodeStrategy {

    MotionAction getAction(RobocodeState state);
}
