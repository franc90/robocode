package pl.edu.agh.robocode.learning;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import piqle.environment.AbstractState;
import piqle.environment.IEnvironment;
import piqle.environment.IState;
import pl.edu.agh.robocode.bots.Distance;
import pl.edu.agh.robocode.bots.RobocodeState;

class RobocodeLearningState extends AbstractState {

    private final RobocodeState robocodeState;

    static RobocodeLearningStateBuilder build() {
        return environment -> state -> new RobocodeLearningState(environment, state);
    }

    static IState initialState(IEnvironment environment) {
        return new RobocodeLearningState(environment, RobocodeState.initialState());
    }

    private RobocodeLearningState(IEnvironment environment, RobocodeState robocodeState) {
        super(environment);
        this.robocodeState = robocodeState;
    }

    public Distance getDistanceToWall() {
        return robocodeState.getDistanceToWall();
    }

    @Override
    public IState copy() {
        return new RobocodeLearningState(getEnvironment(), robocodeState);
    }

    @Override
    public int nnCodingSize() {
        return 0; //used only for neural networks
    }

    @Override
    public double[] nnCoding() {
        return new double[0]; //used only for neural networks
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RobocodeLearningState other = (RobocodeLearningState) o;

        return new EqualsBuilder()
                .append(robocodeState, other.robocodeState)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(robocodeState).
                toHashCode();
    }


    interface RobocodeLearningStateBuilder {

        WithStateFunction withEnvironment(RobocodeLearningEnvironment environment);
    }

    interface WithStateFunction {

        RobocodeLearningState withState(RobocodeState state);
    }

}
