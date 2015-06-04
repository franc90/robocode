package pl.edu.agh.robocode.learning;

import pl.edu.agh.robocode.bot.state.RobocodeState;

public interface EnvironmentProperties {

    RobocodeState getDeafultInitialState();

    double getDisplacementValue();

    double getTurnAngle();
}
