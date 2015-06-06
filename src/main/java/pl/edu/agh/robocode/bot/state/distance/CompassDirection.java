package pl.edu.agh.robocode.bot.state.distance;

import java.util.HashMap;
import java.util.Map;

public enum CompassDirection {
    N, E, S, W, robotDirection;


    private static final Map<CompassDirection, CompassDirection> oppositeDirections;

    static {
        oppositeDirections = new HashMap<CompassDirection, CompassDirection>();
        oppositeDirections.put(N, S);
        oppositeDirections.put(S, N);
        oppositeDirections.put(E, W);
        oppositeDirections.put(W, E);
    }

    public CompassDirection opposite() {
        return oppositeDirections.get(this);
    }
}
