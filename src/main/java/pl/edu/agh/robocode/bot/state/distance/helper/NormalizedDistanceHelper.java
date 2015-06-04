package pl.edu.agh.robocode.bot.state.distance.helper;

import pl.edu.agh.robocode.bot.state.distance.NormalizedDistance;

public class NormalizedDistanceHelper {

    private static final double SMALL = .1;
    private static final double MEDIUM = .5;
    private double diameter;

    public NormalizedDistanceHelper(double mapDiameter) {
        this.diameter = mapDiameter;
    }

    public NormalizedDistance normalize(Double distance) {
        double proportion = distance / diameter;

        if (proportion <= SMALL) {
            return NormalizedDistance.SMALL;
        }

        if (proportion <= MEDIUM) {
            return NormalizedDistance.MEDIUM;
        }

        return NormalizedDistance.BIG;
    }
}
