package pl.edu.agh.robocode.properties.helper;

import pl.edu.agh.robocode.bot.state.RobocodeState;
import pl.edu.agh.robocode.properties.EnvironmentProperties;
import pl.edu.agh.robocode.properties.EnvironmentPropertiesImpl;
import robocode.Robot;

public class EnvironmentPropertiesHelper {

    private static final double DEFAULT_TURN_ANGLE = 30;
    private static final double TO_DISPLACEMENT_PERCENTAGE = .05;


    public EnvironmentProperties generateProperties(RobocodeState state) {
        EnvironmentPropertiesImpl properties = new EnvironmentPropertiesImpl();

        properties.setInitialState(state);
        properties.setTurnAngle(DEFAULT_TURN_ANGLE);
        properties.setDisplacementValue(generateDisplacementValue(state));

        return properties;
    }

    private double generateDisplacementValue(RobocodeState state) {
        double mapHeight = state.getRobotState().getBattlefieldHeight();
        double mapWidth = state.getRobotState().getBattlefieldWidth();

        double xSqr = mapHeight * mapHeight;
        double ySqr = mapWidth * mapWidth;
        double diameter = Math.sqrt(xSqr + ySqr);

        return diameter * TO_DISPLACEMENT_PERCENTAGE;
    }
}
