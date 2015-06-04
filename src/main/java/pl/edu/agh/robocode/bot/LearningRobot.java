package pl.edu.agh.robocode.bot;

import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.bot.state.helper.RobocodeStateHelper;
import pl.edu.agh.robocode.motion.MotionAction;
import robocode.Robot;
import robocode.RoundEndedEvent;

public class LearningRobot extends Robot {

    private RobocodeStrategy strategy;

    private RobocodeStateHelper helper = new RobocodeStateHelper();

    @Override
    public void run() {
        strategy.newRound();

        while (true) {
            RobocodeState state = helper.create(this);
            MotionAction action = strategy.getAction(state);
            performAction(action);
        }

    }

    private void performAction(MotionAction action) {

    }

    @Override
    public void onRoundEnded(RoundEndedEvent event) {
    }
}
