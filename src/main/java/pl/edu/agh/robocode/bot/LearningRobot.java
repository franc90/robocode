package pl.edu.agh.robocode.bot;

import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.bot.state.helper.RoboState;
import pl.edu.agh.robocode.bot.state.helper.RobocodeStateHelper;
import pl.edu.agh.robocode.bot.state.helper.StatePersistingHelper;
import pl.edu.agh.robocode.learning.RobocodeLearningStrategy;
import pl.edu.agh.robocode.motion.MotionAction;
import pl.edu.agh.robocode.motion.StraightMotion;
import pl.edu.agh.robocode.motion.TurnMotion;
import pl.edu.agh.robocode.properties.EnvironmentProperties;
import pl.edu.agh.robocode.properties.helper.EnvironmentPropertiesHelper;
import robocode.*;

import java.io.File;

public class LearningRobot extends AdvancedRobot {

    private static final String STATE_FILE = "robotState";
    
    private RobocodeStrategy strategy;

    private RobocodeStateHelper robocodeStateHelper = new RobocodeStateHelper();

    private EnvironmentPropertiesHelper environmentPropertiesHelper = new EnvironmentPropertiesHelper();

    private StatePersistingHelper<RoboState> helper;

    private EnvironmentProperties properties;

    private RoboState roboState;

    @Override
    public void run() {
        File dataFile = getDataFile(STATE_FILE);
        helper = new StatePersistingHelper<RoboState>(dataFile);
        properties = environmentPropertiesHelper.generateProperties(robocodeStateHelper.create(this));
        strategy = new RobocodeLearningStrategy(properties);

        if (getRoundNum() > 0) {
            roboState = helper.load();
        }

        while (true) {
            if (roboState != null) {
                turnGunLeft(roboState.getNr());
            } else {
                RobocodeState state = robocodeStateHelper.create(this);
                MotionAction action = strategy.getAction(state);
                performAction(action);
            }
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

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        fire(1);
    }

    @Override
    public void onRoundEnded(RoundEndedEvent event) {
        RoboState state = new RoboState();
        state.setNr(getRoundNum() * 4);
        helper.persist(state);
    }

    @Override
    public void onBattleEnded(BattleEndedEvent event) {
        RoboState state = new RoboState();
        state.setNr(getRoundNum() * 4);
        helper.persist(state);
    }

    @Override
    public void onWin(WinEvent event) {
        RoboState state = new RoboState();
        state.setNr(getRoundNum() * 4);
        helper.persist(roboState);
    }
}
