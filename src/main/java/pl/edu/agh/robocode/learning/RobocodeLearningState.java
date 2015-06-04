package pl.edu.agh.robocode.learning;

import piqle.environment.AbstractState;
import piqle.environment.IEnvironment;
import piqle.environment.IState;

class RobocodeLearningState extends AbstractState {

    private int distanceToWall = 100;

    public RobocodeLearningState(IEnvironment ct) {
        super(ct);
    }

    public int getDistanceToWall() {
        return distanceToWall;
    }

    public void setDistanceToWall(int distanceToWall) {
        this.distanceToWall = distanceToWall;
    }

    @Override
    public IState copy() {
        return null;
    }

    @Override
    public int nnCodingSize() {
        return 0;
    }

    @Override
    public double[] nnCoding() {
        return new double[0];
    }

    @Override
    public String toString() {
        return "RobocodeLearningState{" +
                "distanceToWall=" + distanceToWall +
                '}';
    }
}
