package pl.edu.agh.robocode.bot;

import pl.edu.agh.robocode.bot.state.Collisions;
import pl.edu.agh.robocode.bot.state.GunPosition;
import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.bot.state.enemy.Enemies;
import pl.edu.agh.robocode.bot.state.enemy.Enemy;
import pl.edu.agh.robocode.bot.state.helper.GunPositionerHelper;
import pl.edu.agh.robocode.bot.state.helper.RobocodeStateHelper;
import pl.edu.agh.robocode.bot.strategy.RobocodeStrategyDataStore;
import pl.edu.agh.robocode.exception.NullValueException;
import pl.edu.agh.robocode.learning.RobocodeLearningStrategy;
import pl.edu.agh.robocode.learning.RobocodeLearningStrategyDataStore;
import pl.edu.agh.robocode.motion.MotionAction;
import pl.edu.agh.robocode.motion.StraightMotion;
import pl.edu.agh.robocode.motion.TurnSide;
import pl.edu.agh.robocode.properties.EnvironmentProperties;
import pl.edu.agh.robocode.properties.helper.EnvironmentPropertiesHelper;
import robocode.*;

import java.io.File;

public class LearningRobot extends AdvancedRobot {

    private static final String STATE_FILE = "robotState";

    private RobocodeLearningStrategy strategy;

    private RobocodeStateHelper robocodeStateHelper = new RobocodeStateHelper();

    private EnvironmentPropertiesHelper environmentPropertiesHelper = new EnvironmentPropertiesHelper();

    private EnvironmentProperties properties;

    private RobocodeStrategyDataStore<RobocodeLearningStrategy> strategyDataStore;

    private Enemies enemies = new Enemies();

    private Collisions collisions = new Collisions();

    @Override
    public void run() {
        File dataFile = getDataFile(STATE_FILE);
        strategyDataStore = new RobocodeLearningStrategyDataStore(dataFile);
        properties = environmentPropertiesHelper.generateProperties(robocodeStateHelper.create(this));
        strategy = strategyDataStore.load(properties);

        while (true) {
            RobocodeState state = robocodeStateHelper.create(this);
            enemies.clear();
            collisions.clear();
            MotionAction action = strategy.getAction(state);
            performAction(action);
            turnRadarLeft(360);
            findAndShootNearestEnemy();
//            for setXXX methods
//            waitFor(new TurnCompleteCondition(this));
        }

    }

    private void performAction(MotionAction action) {

        TurnSide turnSide = action.getTurnMotion().getTurnSide();
        double angle = action.getTurnMotion().getAngle();
        if (turnSide == TurnSide.LEFT) {
            turnLeft(angle);
        } else
            turnRight(angle);

        if (StraightMotion.FORWARD == action.getStraightMotion()) {
            ahead(properties.getDisplacementValue());
        } else {
            back(properties.getDisplacementValue());
        }

    }

    private void findAndShootNearestEnemy() {
        try {
            Enemy nearest = enemies.getNearest();
            double bearing = nearest.getBearing();
            GunPosition gunPosition = GunPositionerHelper.getGunPosition(bearing, getHeading(), getGunHeading());
            if (gunPosition.getTurnSide() == GunPosition.TurnSide.RIGHT) {
                turnGunRight(gunPosition.getAngle());
            } else if (gunPosition.getTurnSide() == GunPosition.TurnSide.LEFT) {
                turnGunLeft(gunPosition.getAngle());
            }
            fire(1);
        } catch (NullValueException ex) {
            System.out.println("No nearest enemy found");
        }


    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        addEnemy(event);
    }

    @Override
    public void onRoundEnded(RoundEndedEvent event) {
        strategyDataStore.save(strategy);
    }

    @Override
    public void onBattleEnded(BattleEndedEvent event) {
        strategyDataStore.save(strategy);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent event) {
        collisions.hitByBullet();
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {
        collisions.hitOtherRobot();
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        collisions.hitWall();
    }

    private void addEnemy(ScannedRobotEvent event) {
        Enemy enemy = Enemy
                .builder()
                .fromScannedRobotEventAndPosition(event, getX(), getY(), getHeading())
                .withScannedInTurn(getTime())
                .build();

        enemies.addEnemy(event.getName(), enemy);
    }

    public Collisions getCollisions() {
        return collisions;
    }

    public Enemies getEnemies() {
        return enemies;
    }
}
