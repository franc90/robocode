package pl.edu.agh.robocode.learning;

import piqle.environment.AbstractEnvironmentSingle;
import piqle.environment.ActionList;
import piqle.environment.IAction;
import piqle.environment.IState;

import java.util.EnumSet;
import java.util.stream.Stream;

public class RobocodeEnvironment extends AbstractEnvironmentSingle {


    private final boolean isDebugEnable = false;

    @Override
    public ActionList getActionList(IState s) {
        ActionList actionList = new ActionList(s);
        RobocodeState state = (RobocodeState)s;
        if(state.getDistanceToWall() >= 100)
            EnumSet.of(RobocodeAction.GO_DOWN, RobocodeAction.TURN_LEFT, RobocodeAction.TURN_RIGHT)
                    .stream().forEach(actionList::add);
        else
            Stream.of(RobocodeAction.values()).forEach(actionList::add);
        printDebug("ActionList:\n" + actionList);
        return actionList;
    }

    @Override
    public IState successorState(IState s, IAction a) {
        RobocodeState state = (RobocodeState) s;
        RobocodeState newState = new RobocodeState(this);
        RobocodeAction action = (RobocodeAction)a;
        newState.setDistanceToWall(state.getDistanceToWall() + action.getMovement());
        printDebug("New state:" + newState + " for old:" + state + " and action:" + action);
        return newState;
    }

    @Override
    public IState defaultInitialState() {
        return new RobocodeState(this);
    }

    @Override
    public double getReward(IState s1, IState s2, IAction a) {
        RobocodeState oldState = (RobocodeState) s1;
        RobocodeState newState = (RobocodeState) s2;
        printDebug("Reward for:" + oldState + "|" + newState);
        return newState.getDistanceToWall() < oldState.getDistanceToWall() ? 1 : -1;
    }

    @Override
    public boolean isFinal(IState s) {
        RobocodeState state = (RobocodeState) s;
        return state.getDistanceToWall() <= 0;
    }

    @Override
    public int whoWins(IState s) {
        return 0;
    }

    private void printDebug(String debug) {
        if(isDebugEnable)
            System.out.println(debug);
    }
}
