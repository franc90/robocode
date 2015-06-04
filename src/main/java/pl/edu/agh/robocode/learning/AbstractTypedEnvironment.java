package pl.edu.agh.robocode.learning;

import piqle.environment.AbstractEnvironmentSingle;
import piqle.environment.ActionList;
import piqle.environment.IAction;
import piqle.environment.IState;

abstract class AbstractTypedEnvironment<S extends IState, A extends IAction> extends AbstractEnvironmentSingle {

    protected abstract ActionList getActionsList(S state);
    protected abstract S nextState(S state, A action);

    @SuppressWarnings("unchecked")
    @Override
    public ActionList getActionList(IState state) {
        return getActionsList((S) state);
    }

    @SuppressWarnings("unchecked")
    @Override
    public IState successorState(IState state, IAction action) {
        return nextState((S)state, (A) action);
    }

    @Override
    public IState defaultInitialState() {
        return RobocodeLearningState.initialState(this);
    }

    @Override
    public double getReward(IState s1, IState s2, IAction a) {
        return 0;
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
