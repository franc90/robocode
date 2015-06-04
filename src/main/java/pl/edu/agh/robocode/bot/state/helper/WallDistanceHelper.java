package pl.edu.agh.robocode.bot.state.helper;

import pl.edu.agh.robocode.bot.state.RobotState;
import pl.edu.agh.robocode.bot.state.distance.CompassDirection;
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
            wallDistance.setAheadWall(new Wall<>(CompassDirection.N, wallDistance.getNorthWallDistance()));
            wallDistance.setBackWall(new Wall<>(CompassDirection.S, wallDistance.getSouthWallDistance()));
        } else if (heading == 90) {
            wallDistance.setAheadWall(new Wall<>(CompassDirection.W, wallDistance.getWestWallDistance()));
            wallDistance.setBackWall(new Wall<>(CompassDirection.E, wallDistance.getEastWallDistance()));
        } else if (heading == 180) {
            wallDistance.setAheadWall(new Wall<>(CompassDirection.S, wallDistance.getSouthWallDistance()));
            wallDistance.setBackWall(new Wall<>(CompassDirection.N, wallDistance.getNorthWallDistance()));
        } else if (heading == 270) {
            wallDistance.setAheadWall(new Wall<>(CompassDirection.E, wallDistance.getEastWallDistance()));
            wallDistance.setBackWall(new Wall<>(CompassDirection.W, wallDistance.getWestWallDistance()));
        } else {
            double angle = getLineAngle(heading);
            double tangentValue = Math.tan(angle);
            Wall<Double> wall;

            if (heading < 90) {
                wall = getWall(x, y, mapHeight, mapWidth, tangentValue, CompassDirection.W, CompassDirection.N);
                wallDistance.setAheadWall(wall);
                wall = getWall(x, y, mapHeight, mapWidth, tangentValue, CompassDirection.E, CompassDirection.S);
                wallDistance.setBackWall(wall);
            } else if (heading < 180) {
                wall = getWall(x, y, mapHeight, mapWidth, tangentValue, CompassDirection.W, CompassDirection.S);
                wallDistance.setAheadWall(wall);
                wall = getWall(x, y, mapHeight, mapWidth, tangentValue, CompassDirection.E, CompassDirection.N);
                wallDistance.setBackWall(wall);
            } else if (heading < 270) {
                wall = getWall(x, y, mapHeight, mapWidth, tangentValue, CompassDirection.E, CompassDirection.S);
                wallDistance.setAheadWall(wall);
                wall = getWall(x, y, mapHeight, mapWidth, tangentValue, CompassDirection.W, CompassDirection.N);
                wallDistance.setBackWall(wall);
            } else {
                wall = getWall(x, y, mapHeight, mapWidth, tangentValue, CompassDirection.E, CompassDirection.N);
                wallDistance.setAheadWall(wall);
                wall = getWall(x, y, mapHeight, mapWidth, tangentValue, CompassDirection.W, CompassDirection.S);
                wallDistance.setBackWall(wall);
            }
        }
        return null;
    }

    private Wall<Double> getWall(double x, double y, double mapHeight, double mapWidth, double tanAlpha, CompassDirection verticalWall, CompassDirection horizontalWall) {
        double meetY;
        double meetX;
        CompassDirection wallDirection;

        if (verticalWall == CompassDirection.W) {
            wallDirection = CompassDirection.W;
            meetX = 0;
        } else {
            wallDirection = CompassDirection.E;
            meetX = mapWidth;
        }
        meetY = yMeetValue(tanAlpha, meetX, x, y);

        if (!correctWall(meetY, mapHeight)) {
            if (horizontalWall == CompassDirection.N) {
                wallDirection = CompassDirection.N;
                meetY = mapHeight;
            } else {
                wallDirection = CompassDirection.S;
                meetY = 0;
            }
            meetX = xMeetValue(tanAlpha, meetY, x, y);
        }

        return new Wall<>(wallDirection, computeDistance(x, y, meetX, meetY));
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
