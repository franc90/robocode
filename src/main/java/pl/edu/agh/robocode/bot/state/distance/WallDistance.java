package pl.edu.agh.robocode.bot.state.distance;

import pl.edu.agh.robocode.bot.state.distance.helper.NormalizedDistanceHelper;

import java.util.HashMap;
import java.util.Map;

public class WallDistance {

    private Map<CompassDirection, Double> wallDistance = new HashMap<>();

    private Wall<Double> aheadWall;

    private Wall<Double> backWall;

    private NormalizedDistanceHelper helper;

    public WallDistance(double mapDiameter) {
        helper = new NormalizedDistanceHelper(mapDiameter);
    }

    public double getNorthWallDistance() {
        return wallDistance.get(CompassDirection.N);
    }

    public NormalizedDistance getNormalizedNorthWallDistance() {
        return helper.normalize(wallDistance.get(CompassDirection.N));
    }

    public void setNorthWallDistance(double northWall) {
        wallDistance.put(CompassDirection.N, northWall);
    }

    public double getSouthWallDistance() {
        return wallDistance.get(CompassDirection.S);
    }

    public NormalizedDistance getNormalizedSouthWallDistance() {
        return helper.normalize(wallDistance.get(CompassDirection.S));
    }

    public void setSouthWallDistance(double southWall) {
        wallDistance.put(CompassDirection.S, southWall);
    }

    public double getEastWallDistance() {
        return wallDistance.get(CompassDirection.E);
    }

    public NormalizedDistance getNormalizedEastWallDistance() {
        return helper.normalize(wallDistance.get(CompassDirection.E));
    }

    public void setEastWallDistance(double eastWall) {
        wallDistance.put(CompassDirection.E, eastWall);
    }

    public double getWestWallDistance() {
        return wallDistance.get(CompassDirection.W);
    }

    public NormalizedDistance getNormalizedWestWallDistance() {
        return helper.normalize(wallDistance.get(CompassDirection.W));
    }

    public void setWestWallDistance(double westWall) {
        wallDistance.put(CompassDirection.W, westWall);
    }

    public double getAheadWallDistance() {
        return aheadWall.getDistance();
    }

    public NormalizedDistance getNormalizedAheadWallDistance() {
        return helper.normalize(aheadWall.getDistance());
    }

    public Wall<Double> getAheadWall() {
        return aheadWall;
    }

    public Wall<Double> getBackWall() {
        return backWall;
    }

    public void setAheadWall(Wall<Double> aheadWall) {
        this.aheadWall = aheadWall;
    }

    public double getBackWallDistance() {
        return backWall.getDistance();
    }

    public NormalizedDistance getNormalizedBackWallDistance() {
        return helper.normalize(backWall.getDistance());
    }

    public void setBackWall(Wall<Double> backWall) {
        this.backWall = backWall;
    }

    public Wall<Double> getNearestWall() {
        Map.Entry<CompassDirection, Double> value = wallDistance
                .entrySet()
                .stream()
                .min((x, y) -> x.getValue().compareTo(y.getValue()))
                .get();

        return new Wall<>(value.getKey(), value.getValue());
    }

    public Wall<NormalizedDistance> getNormalizedNearestWallDistance() {
        Wall<Double> nearestWall = getNearestWall();
        NormalizedDistance normalizedDistance = helper.normalize(nearestWall.getDistance());
        return new Wall<>(nearestWall.getDirection(), normalizedDistance);
    }
}
