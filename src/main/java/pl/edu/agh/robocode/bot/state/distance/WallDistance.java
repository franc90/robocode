package pl.edu.agh.robocode.bot.state.distance;

import pl.edu.agh.robocode.bot.state.distance.helper.NormalizedDistanceHelper;

import java.util.HashMap;
import java.util.Map;

public class WallDistance {

    private Map<Wall.WallDirection, Double> wallDistance = new HashMap<>();

    private double aheadWallDistance;

    private double backWallDistance;


    private NormalizedDistanceHelper helper;

    public WallDistance(double mapDiameter) {
        helper = new NormalizedDistanceHelper(mapDiameter);
    }

    public double getNorthWallDistance() {
        return wallDistance.get(Wall.WallDirection.N);
    }

    public NormalizedDistance getNormalizedNorthWallDistance() {
        return helper.normalize(wallDistance.get(Wall.WallDirection.N));
    }

    public void setNorthWallDistance(double northWall) {
        wallDistance.put(Wall.WallDirection.N, northWall);
    }

    public double getSouthWallDistance() {
        return wallDistance.get(Wall.WallDirection.S);
    }

    public NormalizedDistance getNormalizedSouthWallDistance() {
        return helper.normalize(wallDistance.get(Wall.WallDirection.S));
    }

    public void setSouthWallDistance(double southWall) {
        wallDistance.put(Wall.WallDirection.S, southWall);
    }

    public double getEastWallDistance() {
        return wallDistance.get(Wall.WallDirection.E);
    }

    public NormalizedDistance getNormalizedEastWallDistance() {
        return helper.normalize(wallDistance.get(Wall.WallDirection.E));
    }

    public void setEastWallDistance(double eastWall) {
        wallDistance.put(Wall.WallDirection.E, eastWall);
    }

    public double getWestWallDistance() {
        return wallDistance.get(Wall.WallDirection.W);
    }

    public NormalizedDistance getNormalizedWestWallDistance() {
        return helper.normalize(wallDistance.get(Wall.WallDirection.W));
    }

    public void setWestWallDistance(double westWall) {
        wallDistance.put(Wall.WallDirection.W, westWall);
    }

    public double getAheadWallDistance() {
        return aheadWallDistance;
    }

    public NormalizedDistance getNormalizedAheadWallDistance() {
        return helper.normalize(aheadWallDistance);
    }


    public void setAheadWallDistance(double aheadWall) {
        this.aheadWallDistance = aheadWall;
    }

    public double getBackWallDistance() {
        return backWallDistance;
    }

    public NormalizedDistance getNormalizedBackWallDistance() {
        return helper.normalize(backWallDistance);
    }

    public void setBackWallDistance(double backWall) {
        this.backWallDistance = backWall;
    }

    public Wall<Double> getNearestWall() {
        Map.Entry<Wall.WallDirection, Double> value = wallDistance
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
