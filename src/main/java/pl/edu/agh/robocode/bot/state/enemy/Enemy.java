package pl.edu.agh.robocode.bot.state.enemy;

import pl.edu.agh.robocode.bot.state.distance.CompassDirection;
import pl.edu.agh.robocode.bot.state.distance.NormalizedDistance;
import pl.edu.agh.robocode.bot.state.distance.helper.CompassDirectionHelper;
import pl.edu.agh.robocode.bot.state.distance.helper.NormalizedDistanceHelper;
import robocode.ScannedRobotEvent;

public class Enemy {

    private String name;

    private double x;

    private double y;

    private double bearing;

    private double distance;

    private double energy;

    private double heading;

    private double velocity;

    private long scannedInTurn;

    private NormalizedDistanceHelper normalizedDistanceHelper;

    public Enemy(String name, double x, double y, double bearing, double distance, double energy, double heading, double velocity, long scannedInTurn, double diameter) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.bearing = bearing;
        this.distance = distance;
        this.energy = energy;
        this.heading = heading;
        this.velocity = velocity;
        this.scannedInTurn = scannedInTurn;
        this.normalizedDistanceHelper = new NormalizedDistanceHelper(diameter);
    }

    public String getName() {
        return name;
    }

    /**
     * @return -1 when value unknown
     */
    public double getX() {
        return x;
    }


    /**
     * @return -1 when value unknown
     */
    public double getY() {
        return y;
    }


    /**
     * @return angle in [-180,180]
     */
    public double getBearing() {
        return bearing;
    }

    /**
     * Returns normalized bearing:<br>
     * E - right robot side (not global east)<br>
     * W - left robot side<br>
     * S - behind robot<br>
     * N - ahead robot
     */
    public CompassDirection getNormalizedBearing() {
        return CompassDirectionHelper.normalize(bearing);
    }

    public double getDistance() {
        return distance;
    }

    public NormalizedDistance getNormalizedDistance() {
        return normalizedDistanceHelper.normalize(distance);
    }

    public double getEnergy() {
        return energy;
    }

    public double getHeading() {
        return heading;
    }

    /**
     * Returns normalized bearing:<br>
     * E - global right<br>
     * W - global left<br>
     * S - global down<br>
     * N - global up
     */
    public CompassDirection getNormalizedHeading() {
        return CompassDirectionHelper.normalize(heading);
    }

    public double getVelocity() {
        return velocity;
    }

    public long getScannedInTurn() {
        return scannedInTurn;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;

        private double x = -1;

        private double y = -1;

        private double bearing;

        private double distance;

        private double energy;

        private double heading;

        private double velocity;

        private long scannedInTurn;

        private double mapDiameter;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withBearing(double bearing) {
            this.bearing = bearing;
            return this;
        }

        public Builder withDistance(double distance) {
            this.distance = distance;
            return this;
        }

        public Builder withEnergy(double energy) {
            this.energy = energy;
            return this;
        }

        public Builder withHeading(double heading) {
            this.heading = heading;
            return this;
        }

        public Builder withVelocity(double velocity) {
            this.velocity = velocity;
            return this;
        }

        public Builder withScannedInTurn(long turn) {
            this.scannedInTurn = turn;
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

        public Builder withMapDiameter(double mapDiameter) {
            this.mapDiameter = mapDiameter;
            return this;
        }

        public Builder fromScannedRobotEventAndPosition(ScannedRobotEvent event, double scannerXPosition, double scannerYPosition, double scannerHeading) {
            withName(event.getName());
            withBearing(event.getBearing());
            withDistance(event.getDistance());
            withEnergy(event.getEnergy());
            withHeading(event.getHeading());
            withVelocity(event.getVelocity());

            double lineAngle = getLineAngle(event.getHeading(), scannerHeading);
            x = scannerXPosition + Math.cos(lineAngle) * event.getDistance();
            y = scannerYPosition + Math.sin(lineAngle) * event.getDistance();

            return this;
        }

        public Enemy build() {
            return new Enemy(name, x, y, bearing, distance, energy, heading, velocity, scannedInTurn, mapDiameter);
        }

        private double getLineAngle(double heading, double scannerHeading) {
            double totalHeading = (heading + scannerHeading) % CompassDirectionHelper.FULL_CIRCLE;

            if (270 <= totalHeading && totalHeading <= 360) {
                return 450 - totalHeading;
            }
            return 90 - totalHeading;
        }
    }

}
