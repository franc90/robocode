package pl.edu.agh.robocode.bots;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RobocodeState {

    private Distance distanceToWall;

    public static RobocodeState initialState() {
        RobocodeState state = new RobocodeState();
        state.setDistanceToWall(Distance.BIG);
        return state;
    }

    public Distance getDistanceToWall() {
        return distanceToWall;
    }

    public void setDistanceToWall(Distance distanceToWall) {
        this.distanceToWall = distanceToWall;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RobocodeState))
            return false;
        if (obj == this)
            return true;
        RobocodeState other = (RobocodeState) obj;

        return new EqualsBuilder().
                append(distanceToWall, other.distanceToWall).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                append(distanceToWall).
                toHashCode();
    }

}
