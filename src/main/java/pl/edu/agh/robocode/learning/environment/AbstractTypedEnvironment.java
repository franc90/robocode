package pl.edu.agh.robocode.learning.environment;

import piqle.environment.AbstractEnvironmentSingle;
import piqle.environment.ActionList;
import piqle.environment.IAction;
import piqle.environment.IState;

abstract class AbstractTypedEnvironment<S extends IState, A extends IAction> extends AbstractEnvironmentSingle {

    protected abstract ActionList getActionsList(S state);

    protected abstract S nextState(S state, A action);

    protected abstract double calculateReward(S oldState, S newState, A action);

    protected abstract boolean isStateFinal(S state);

    @SuppressWarnings("unchecked")
    @Override
    public ActionList getActionList(IState state) {
        return getActionsList((S) state);
    }

    @SuppressWarnings("unchecked")
    @Override
    public IState successorState(IState state, IAction action) {
        return nextState((S) state, (A) action);
    }

    @SuppressWarnings("unchecked")
    @Override
    public double getReward(IState oldState, IState newState, IAction action) {
        return calculateReward((S) oldState, (S) newState, (A) action);
    }

    @Override
    public boolean isFinal(IState state) {
        return false;
    }

    @Override
    public int whoWins(IState s) {
        return 0;
    }

}
