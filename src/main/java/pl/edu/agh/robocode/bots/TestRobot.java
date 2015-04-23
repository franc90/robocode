package pl.edu.agh.robocode.bots;

import robocode.HitByBulletEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class TestRobot extends Robot {

    @Override
    public void run() {
        while (true) {
            ahead(100);
            turnGunLeft(360);
            back(100);
            turnGunRight(360);
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        fire(1);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent event) {
        turnLeft(90);
    }
}
