package pl.edu.agh.robocode.bot.state.distance;

public class Wall<T> {

    public enum WallDirection {
        N, E, S, W
    }

    private WallDirection direction;
    private T distance;

    public Wall(WallDirection direction, T distance) {
        this.direction = direction;
        this.distance = distance;
    }

    public WallDirection getDirection() {
        return direction;
    }

    public void setDirection(WallDirection direction) {
        this.direction = direction;
    }

    public T getDistance() {
        return distance;
    }

    public void setDistance(T distance) {
        this.distance = distance;
    }
}
