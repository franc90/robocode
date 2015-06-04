package pl.edu.agh.robocode.bot;

import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.bot.state.helper.RobocodeStateHelper;
import pl.edu.agh.robocode.learning.RobocodeLearningStrategy;
import pl.edu.agh.robocode.motion.MotionAction;
import pl.edu.agh.robocode.motion.StraightMotion;
import pl.edu.agh.robocode.motion.TurnMotion;
import pl.edu.agh.robocode.properties.EnvironmentProperties;
import pl.edu.agh.robocode.properties.helper.EnvironmentPropertiesHelper;
import robocode.AdvancedRobot;
import robocode.Robot;

public class LearningRobot extends AdvancedRobot {

    private RobocodeStrategy strategy;

    private RobocodeStateHelper robocodeStateHelper = new RobocodeStateHelper();

    private EnvironmentPropertiesHelper environmentPropertiesHelper = new EnvironmentPropertiesHelper();

    private EnvironmentProperties properties;

    @Override
    public void run() {
        properties = environmentPropertiesHelper.generateProperties(robocodeStateHelper.create(this));
        strategy = new RobocodeLearningStrategy(properties);

        while (true) {
            RobocodeState state = robocodeStateHelper.create(this);
            MotionAction action = strategy.getAction(state);
            performAction(action);
        }

    }

    private void performAction(MotionAction action) {
        if (action.getStraightMotion().equals(StraightMotion.FORWARD)) {
            ahead(properties.getDisplacementValue());
        } else {
            back(properties.getDisplacementValue());
        }

        if (action.getTurnMotion().equals(TurnMotion.LEFT)) {
            turnLeft(properties.getTurnAngle());
        } else if (action.getTurnMotion().equals(TurnMotion.RIGHT)) {
            turnRight(properties.getTurnAngle());
        }
    }
}
