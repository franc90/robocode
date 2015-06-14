package pl.edu.agh.robocode.bot;

import pl.edu.agh.robocode.bot.state.Collisions;
import pl.edu.agh.robocode.bot.state.GunPosition;
import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.bot.state.enemy.Enemies;
import pl.edu.agh.robocode.bot.state.enemy.Enemy;
import pl.edu.agh.robocode.bot.state.helper.GunPositionerHelper;
import pl.edu.agh.robocode.bot.state.helper.RobocodeStateHelper;
import pl.edu.agh.robocode.bot.statistics.Statistics;
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
    private static final String WALL_HITS_FILE = "wall_hits.txt";
    private static final String ENEMY_HITS_FILE = "enemy_hits.txt";

    private RobocodeLearningStrategy strategy;

    private RobocodeStateHelper robocodeStateHelper = new RobocodeStateHelper();

    private EnvironmentPropertiesHelper environmentPropertiesHelper = new EnvironmentPropertiesHelper();

    private EnvironmentProperties properties;

    private RobocodeStrategyDataStore<RobocodeLearningStrategy> strategyDataStore;

    private Enemies enemies = new Enemies();

    private Collisions collisions = new Collisions();

    private Collisions roundCollisions = new Collisions();
    private Statistics wallHitsStats;
    private Statistics enemyHitsStats;

    @Override
    public void run() {
        wallHitsStats = new Statistics(getDataFile(WALL_HITS_FILE));
        enemyHitsStats = new Statistics(getDataFile(ENEMY_HITS_FILE));
        strategyDataStore = new RobocodeLearningStrategyDataStore(getDataFile(STATE_FILE), getDataFile("agent"), getDataFile("rewards.txt"));
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
        System.out.println("Walls hit: " + roundCollisions.getHitWall());
        System.out.println("Other robot hit: " + roundCollisions.getHitOtherRobot());
        System.out.println("Bullet hit: " + roundCollisions.getHitByBullet());
        wallHitsStats.save(Integer.toString(roundCollisions.getHitWall()));
        enemyHitsStats.save(Integer.toString(roundCollisions.getHitOtherRobot()));
    }

    @Override
    public void onBattleEnded(BattleEndedEvent event) {
        strategyDataStore.save(strategy);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent event) {
        collisions.hitByBullet();
        roundCollisions.hitByBullet();
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {
        collisions.hitOtherRobot();
        roundCollisions.hitOtherRobot();
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        collisions.hitWall();
        roundCollisions.hitWall();
    }

    private void addEnemy(ScannedRobotEvent event) {
        Enemy enemy = Enemy
                .builder()
                .fromScannedRobotEventAndPosition(event, getX(), getY(), getHeading())
                .withScannedInTurn(getTime())
                .withMapDiameter(computeDiamter(getBattleFieldHeight(), getBattleFieldWidth()))
                .build();

        enemies.addEnemy(event.getName(), enemy);
    }

    private double computeDiamter(double x, double y) {
        double xVal = x * x;
        double yVal = y * y;
        return Math.sqrt(xVal + yVal);
    }

    public Collisions getCollisions() {
        return collisions;
    }

    public Enemies getEnemies() {
        return enemies;
    }
}
