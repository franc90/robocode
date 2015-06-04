package pl.edu.agh.robocode.learning;

import piqle.environment.AbstractEnvironmentSingle;
import piqle.environment.ActionList;
import piqle.environment.IAction;
import piqle.environment.IState;

import java.util.EnumSet;
import java.util.stream.Stream;

class RobocodeLearningEnvironment extends AbstractEnvironmentSingle {


    private final boolean isDebugEnable = false;

    @Override
    public ActionList getActionList(IState s) {
        return null;
    }

    @Override
    public IState successorState(IState s, IAction a) {
        return null;
    }

    @Override
    public IState defaultInitialState() {
        return null;
    }

    @Override
    public double getReward(IState s1, IState s2, IAction a) {
        return 0.0;
    }

    @Override
    public boolean isFinal(IState s) {
        return false;
    }

    @Override
    public int whoWins(IState s) {
        return 0;
    }


}
