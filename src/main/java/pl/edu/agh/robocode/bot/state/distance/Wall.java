package pl.edu.agh.robocode.bot.state.distance;

public class Wall<T> {

    private CompassDirection direction;
    private T distance;

    public Wall(CompassDirection direction, T distance) {
        this.direction = direction;
        this.distance = distance;
    }

    public CompassDirection getDirection() {
        return direction;
    }

    public void setDirection(CompassDirection direction) {
        this.direction = direction;
    }

    public T getDistance() {
        return distance;
    }

    public void setDistance(T distance) {
        this.distance = distance;
    }
}
