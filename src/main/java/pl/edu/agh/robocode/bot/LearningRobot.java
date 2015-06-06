package pl.edu.agh.robocode.bot;

import piqle.agents.LoneAgent;
import pl.edu.agh.robocode.bot.state.GunPosition;
import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.bot.state.enemy.Enemies;
import pl.edu.agh.robocode.bot.state.enemy.Enemy;
import pl.edu.agh.robocode.bot.state.helper.GunPositionerHelper;
import pl.edu.agh.robocode.bot.state.helper.RobocodeStateHelper;
import pl.edu.agh.robocode.bot.state.helper.StatePersistingHelper;
import pl.edu.agh.robocode.learning.RobocodeLearningStrategy;
import pl.edu.agh.robocode.motion.MotionAction;
import pl.edu.agh.robocode.motion.StraightMotion;
import pl.edu.agh.robocode.motion.TurnMotion;
import pl.edu.agh.robocode.properties.EnvironmentProperties;
import pl.edu.agh.robocode.properties.helper.EnvironmentPropertiesHelper;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.io.File;

public class LearningRobot extends AdvancedRobot {

    private static final String STATE_FILE = "robotState";

    private RobocodeStrategy strategy;

    private RobocodeStateHelper robocodeStateHelper = new RobocodeStateHelper();

    private EnvironmentPropertiesHelper environmentPropertiesHelper = new EnvironmentPropertiesHelper();

    private StatePersistingHelper<LoneAgent> loneAgentPersistHelper;

    private EnvironmentProperties properties;

    private Enemies enemies = new Enemies();

    @Override
    public void run() {
        File dataFile = getDataFile(STATE_FILE);
        loneAgentPersistHelper = new StatePersistingHelper<LoneAgent>(dataFile);
        properties = environmentPropertiesHelper.generateProperties(robocodeStateHelper.create(this));
        strategy = new RobocodeLearningStrategy(properties);

        if (getRoundNum() > 0) {
//            roboState = helper.load();
        }

        while (true) {
            RobocodeState state = robocodeStateHelper.create(this);
            MotionAction action = strategy.getAction(state);
            performAction(action);
            turnRadarLeft(360);
            findAndShootNearestEnemy();
//            for setXXX methods
//            waitFor(new TurnCompleteCondition(this));
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

    private void findAndShootNearestEnemy() {
        Enemy nearest = enemies.getNearest();
        double bearing = nearest.getBearing();

        GunPosition gunPosition = GunPositionerHelper.getGunPosition(bearing, getHeading(), getGunHeading());
        if (gunPosition.getTurnSide() == GunPosition.TurnSide.RIGHT) {
            turnGunRight(gunPosition.getAngle());
        } else if (gunPosition.getTurnSide() == GunPosition.TurnSide.LEFT) {
            turnGunLeft(gunPosition.getAngle());
        }

        fire(1);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        addEnemy(event);
//        fire(1);
    }

    private void addEnemy(ScannedRobotEvent event) {
        Enemy enemy = Enemy
                .builder()
                .fromScannedRobotEventAndPosition(event, getX(), getY(), getHeading())
                .withScannedInTurn(getTime())
                .build();

        enemies.addEnemy(event.getName(), enemy);
    }

    public Enemies getEnemies() {
        return enemies;
    }
}
