package pl.edu.agh.robocode.bot;

import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.bot.state.helper.RobocodeStateHelper;
import pl.edu.agh.robocode.learning.RobocodeLearningStrategy;
import pl.edu.agh.robocode.motion.MotionAction;
import pl.edu.agh.robocode.motion.StraightMotion;
import pl.edu.agh.robocode.motion.TurnMotion;
import pl.edu.agh.robocode.properties.EnvironmentProperties;
import pl.edu.agh.robocode.properties.helper.EnvironmentPropertiesHelper;
import robocode.Robot;
import robocode.RoundEndedEvent;

public class LearningRobot extends Robot {

    @Override
    public void run() {
//        RobocodeStateHelper robocodeStateHelper = new RobocodeStateHelper();
//        EnvironmentPropertiesHelper environmentPropertiesHelper = new EnvironmentPropertiesHelper();
//        EnvironmentProperties properties = environmentPropertiesHelper.generateProperties(robocodeStateHelper.create(this));
//        RobocodeStrategy strategy = new RobocodeLearningStrategy(properties);

//            RobocodeState state = robocodeStateHelper.create(this);
//            MotionAction action = strategy.getAction(state);
//            performAction(action, properties);
            System.out.println("Dupa");


    }

    /*private void performAction(MotionAction action, EnvironmentProperties properties) {
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
    }*/
}
