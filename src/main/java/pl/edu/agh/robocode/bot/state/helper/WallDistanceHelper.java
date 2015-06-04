package pl.edu.agh.robocode.bot.state.helper;

import pl.edu.agh.robocode.bot.state.RobotState;
import pl.edu.agh.robocode.bot.state.distance.Wall;
import pl.edu.agh.robocode.bot.state.distance.WallDistance;

public class WallDistanceHelper {

    public WallDistance compute(RobotState robotState) {
        double x = robotState.getX();
        double y = robotState.getY();
        double mapHeight = robotState.getBattlefieldHeight();
        double mapWidth = robotState.getBattlefieldWidth();

        WallDistance wallDistance = new WallDistance(computeDistance(mapWidth, mapHeight, 0, 0));
        wallDistance.setSouthWallDistance(y);
        wallDistance.setWestWallDistance(x);
        wallDistance.setNorthWallDistance(mapHeight - y);
        wallDistance.setEastWallDistance(mapWidth - x);

        double heading = robotState.getHeading();
        if (heading == 0) {
            wallDistance.setAheadWallDistance(wallDistance.getNorthWallDistance());
            wallDistance.setBackWallDistance(wallDistance.getSouthWallDistance());
        } else if (heading == 90) {
            wallDistance.setAheadWallDistance(wallDistance.getWestWallDistance());
            wallDistance.setBackWallDistance(wallDistance.getEastWallDistance());
        } else if (heading == 180) {
            wallDistance.setAheadWallDistance(wallDistance.getSouthWallDistance());
            wallDistance.setBackWallDistance(wallDistance.getNorthWallDistance());
        } else if (heading == 270) {
            wallDistance.setAheadWallDistance(wallDistance.getEastWallDistance());
            wallDistance.setBackWallDistance(wallDistance.getWestWallDistance());
        } else {
            double angle = getLineAngle(heading);
            double tangentValue = Math.tan(angle);
            double distance;

            if (heading < 90) {
                distance = getWallDistance(x, y, mapHeight, mapWidth, tangentValue, Wall.WallDirection.W, Wall.WallDirection.N);
                wallDistance.setAheadWallDistance(distance);
                distance = getWallDistance(x, y, mapHeight, mapWidth, tangentValue, Wall.WallDirection.E, Wall.WallDirection.S);
                wallDistance.setBackWallDistance(distance);
            } else if (heading < 180) {
                distance = getWallDistance(x, y, mapHeight, mapWidth, tangentValue, Wall.WallDirection.W, Wall.WallDirection.S);
                wallDistance.setAheadWallDistance(distance);
                distance = getWallDistance(x, y, mapHeight, mapWidth, tangentValue, Wall.WallDirection.E, Wall.WallDirection.N);
                wallDistance.setBackWallDistance(distance);
            } else if (heading < 270) {
                distance = getWallDistance(x, y, mapHeight, mapWidth, tangentValue, Wall.WallDirection.E, Wall.WallDirection.S);
                wallDistance.setAheadWallDistance(distance);
                distance = getWallDistance(x, y, mapHeight, mapWidth, tangentValue, Wall.WallDirection.W, Wall.WallDirection.N);
                wallDistance.setBackWallDistance(distance);
            } else {
                distance = getWallDistance(x, y, mapHeight, mapWidth, tangentValue, Wall.WallDirection.E, Wall.WallDirection.N);
                wallDistance.setAheadWallDistance(distance);
                distance = getWallDistance(x, y, mapHeight, mapWidth, tangentValue, Wall.WallDirection.W, Wall.WallDirection.S);
                wallDistance.setBackWallDistance(distance);
            }
        }
        return null;
    }

    private double getWallDistance(double x, double y, double mapHeight, double mapWidth, double tanAlpha, Wall.WallDirection verticalWall, Wall.WallDirection horizontalWall) {
        double meetY;
        double meetX;

        if (verticalWall == Wall.WallDirection.W) {
            meetX = 0;
        } else {
            meetX = mapWidth;
        }
        meetY = yMeetValue(tanAlpha, meetX, x, y);

        if (!correctWall(meetY, mapHeight)) {
            if (horizontalWall == Wall.WallDirection.N) {
                meetY = mapHeight;
            } else {
                meetY = 0;
            }
            meetX = xMeetValue(tanAlpha, meetY, x, y);
        }

        return computeDistance(x, y, meetX, meetY);
    }

    private double computeDistance(double x, double y, double x2, double y2) {
        double xVal = x2 - x;
        double yVal = y2 - y;
        xVal *= xVal;
        yVal *= yVal;
        return Math.sqrt(xVal + yVal);
    }

    private double yMeetValue(double tan, double x, double startX, double startY) {
        return tan * x + startY - startX * tan;
    }

    private double xMeetValue(double tan, double y, double startX, double startY) {
        return (y - startY) / tan + startX;
    }

    private boolean correctWall(double val, double maxVal) {
        return 0 <= val && val <= maxVal;
    }

    private double getLineAngle(double heading) {
        if (heading > 270) {
            return heading - 270;
        }
        if (heading > 90) {
            return heading - 90;
        }
        return 90 + heading;
    }
}
