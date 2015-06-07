package pl.edu.agh.robocode.bot.state;


import pl.edu.agh.robocode.bot.LearningRobot;

public class RobotState {

    private final double energy;

    private final double x;

    private final double y;

    private final double height;

    private final double width;

    private final double velocity;

    private final double heading;

    private final double gunHeading;

    private final double radarHeading;

    private final double battlefieldHeight;

    private final double battlefieldWidth;

    private final int leftOpponentsNumber;

    private final long turnNumber;

    private final long roundNumber;

    private final int hitByBullet;

    private final int hitOtherRobot;

    private final int hitWall;

    public RobotState(double energy, double x, double y, double height, double width, double velocity, double heading,
                      double gunHeading, double radarHeading, double battlefieldHeight, double battlefieldWidth,
                      int leftOpponentsNumber, long turnNumber, long roundNumber, int hitByBullet, int hitOtherRobot, int hitWall) {
        this.energy = energy;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.velocity = velocity;
        this.heading = heading;
        this.gunHeading = gunHeading;
        this.radarHeading = radarHeading;
        this.battlefieldHeight = battlefieldHeight;
        this.battlefieldWidth = battlefieldWidth;
        this.leftOpponentsNumber = leftOpponentsNumber;
        this.turnNumber = turnNumber;
        this.roundNumber = roundNumber;
        this.hitByBullet = hitByBullet;
        this.hitOtherRobot = hitOtherRobot;
        this.hitWall = hitWall;
    }

    public double getEnergy() {
        return energy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getHeading() {
        return heading;
    }

    public double getGunHeading() {
        return gunHeading;
    }

    public double getRadarHeading() {
        return radarHeading;
    }

    public double getBattlefieldHeight() {
        return battlefieldHeight;
    }

    public double getBattlefieldWidth() {
        return battlefieldWidth;
    }

    public int getLeftOpponentsNumber() {
        return leftOpponentsNumber;
    }

    public long getTurnNumber() {
        return turnNumber;
    }

    public long getRoundNumber() {
        return roundNumber;
    }

    public int getHitByBullet() {
        return hitByBullet;
    }

    public int getHitOtherRobot() {
        return hitOtherRobot;
    }

    public int getHitWall() {
        return hitWall;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private double energy;
        private double x;
        private double y;
        private double height;
        private double width;
        private double velocity;
        private double heading;
        private double gunHeading;
        private double radarHeading;
        private double battlefieldHeight;
        private double battlefieldWidth;
        private int leftOpponentsNumber;
        private long turnNumber;
        private long roundNumber;
        private int hitByBullet;
        private int hitOtherRobot;
        private int hitWall;

        public Builder withEnergy(double energy) {
            this.energy = energy;
            return this;
        }

        public Builder withX(double x) {
            this.x = x;
            return this;
        }

        public Builder withY(double y) {
            this.y = y;
            return this;
        }

        public Builder withHeight(double height) {
            this.height = height;
            return this;
        }

        public Builder withWidth(double width) {
            this.width = width;
            return this;
        }

        public Builder withVelocity(double velocity) {
            this.velocity = velocity;
            return this;
        }

        public Builder withHeading(double heading) {
            this.heading = heading;
            return this;
        }

        public Builder withGunHeading(double gunHeading) {
            this.gunHeading = gunHeading;
            return this;
        }

        public Builder withRadarHeading(double radarHeading) {
            this.radarHeading = radarHeading;
            return this;
        }

        public Builder withBattlefieldHeight(double battlefieldHeight) {
            this.battlefieldHeight = battlefieldHeight;
            return this;
        }

        public Builder withBattlefieldWidth(double battlefieldWidth) {
            this.battlefieldWidth = battlefieldWidth;
            return this;
        }

        public Builder withLeftOpponentsNumber(int leftOpponentsNumber) {
            this.leftOpponentsNumber = leftOpponentsNumber;
            return this;
        }

        public Builder withTurnNumber(long turnNumber) {
            this.turnNumber = turnNumber;
            return this;
        }

        public Builder withRoundNumber(long roundNumber) {
            this.roundNumber = roundNumber;
            return this;
        }

        public Builder withHitWall(int hitWall) {
            this.hitWall = hitWall;
            return this;
        }

        public Builder withHitOtherRobot(int hitOtherRobot) {
            this.hitOtherRobot = hitOtherRobot;
            return this;
        }

        public Builder withHitByBullet(int hitByBullet) {
            this.hitByBullet = hitByBullet;
            return this;
        }

        public Builder fromRobot(LearningRobot robot) {
            withEnergy(robot.getEnergy());
            withX(robot.getX());
            withY(robot.getY());
            withHeight(robot.getHeight());
            withWidth(robot.getWidth());
            withVelocity(robot.getVelocity());
            withHeading(robot.getHeading());
            withGunHeading(robot.getGunHeading());
            withRadarHeading(robot.getRadarHeading());
            withBattlefieldHeight(robot.getBattleFieldHeight());
            withBattlefieldWidth(robot.getBattleFieldWidth());
            withLeftOpponentsNumber(robot.getOthers());
            withTurnNumber(robot.getTime());
            withRoundNumber(robot.getRoundNum());
            withHitByBullet(robot.getCollisions().getHitByBullet());
            withHitOtherRobot(robot.getCollisions().getHitOtherRobot());
            withHitWall(robot.getCollisions().getHitWall());
            return this;
        }

        public Builder fromRobotState(RobotState robot) {
            withEnergy(robot.getEnergy());
            withX(robot.getX());
            withY(robot.getY());
            withHeight(robot.getHeight());
            withWidth(robot.getWidth());
            withVelocity(robot.getVelocity());
            withHeading(robot.getHeading());
            withGunHeading(robot.getGunHeading());
            withRadarHeading(robot.getRadarHeading());
            withBattlefieldHeight(robot.getBattlefieldHeight());
            withBattlefieldWidth(robot.getBattlefieldWidth());
            withLeftOpponentsNumber(robot.getLeftOpponentsNumber());
            withTurnNumber(robot.getTurnNumber());
            withRoundNumber(robot.getRoundNumber());
            withHitByBullet(robot.getHitByBullet());
            withHitOtherRobot(robot.getHitOtherRobot());
            withHitWall(robot.getHitWall());
            return this;
        }

        public RobotState build() {
            return new RobotState(energy, x, y, height, width, velocity, heading, gunHeading, radarHeading, battlefieldHeight,
                    battlefieldWidth, leftOpponentsNumber, turnNumber, roundNumber, hitByBullet, hitOtherRobot, hitWall);
        }
    }
}
