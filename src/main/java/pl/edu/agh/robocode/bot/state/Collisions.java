package pl.edu.agh.robocode.bot.state;

public class Collisions {

    private int hitByBullet;

    private int hitOtherRobot;

    private int hitWall;

    public int getHitByBullet() {
        return hitByBullet;
    }

    public int getHitOtherRobot() {
        return hitOtherRobot;
    }

    public int getHitWall() {
        return hitWall;
    }

    public void hitByBullet() {
        ++hitByBullet;
    }

    public void hitOtherRobot() {
        ++hitOtherRobot;
    }

    public void hitWall() {
        ++hitWall;
    }

    public void clear() {
        hitByBullet = 0;
        hitOtherRobot = 0;
        hitWall = 0;
    }

}
