package pl.edu.agh.robocode.properties;

import pl.edu.agh.robocode.bot.state.RobocodeState;

import java.io.Serializable;

public class EnvironmentPropertiesImpl implements EnvironmentProperties, Serializable {

    private transient RobocodeState initialState;
    private double displacementValue;
    private double turnAngle;


    @Override
    public RobocodeState getInitialState() {
        return initialState;
    }

    @Override
    public double getDisplacementValue() {
        return displacementValue;
    }

    @Override
    public double getTurnAngle() {
        return turnAngle;
    }

    public void setInitialState(RobocodeState initialState) {
        this.initialState = initialState;
    }

    public void setDisplacementValue(double displacementValue) {
        this.displacementValue = displacementValue;
    }

    public void setTurnAngle(double turnAngle) {
        this.turnAngle = turnAngle;
    }
}
