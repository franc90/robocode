package pl.edu.agh.robocode.properties;

import pl.edu.agh.robocode.bot.state.RobocodeState;

public interface EnvironmentProperties {

    RobocodeState getInitialState();

    double getDisplacementValue();

    double getTurnAngle();
}
