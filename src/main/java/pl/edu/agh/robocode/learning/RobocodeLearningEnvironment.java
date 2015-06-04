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
        ActionList actionList = new ActionList(s);
        RobocodeLearningState state = (RobocodeLearningState)s;
        if(state.getDistanceToWall() >= 100)
            EnumSet.of(RobocodeLearningAction.GO_DOWN, RobocodeLearningAction.TURN_LEFT, RobocodeLearningAction.TURN_RIGHT)
                    .stream().forEach(actionList::add);
        else
            Stream.of(RobocodeLearningAction.values()).forEach(actionList::add);
        printDebug("ActionList:\n" + actionList);
        return actionList;
    }

    @Override
    public IState successorState(IState s, IAction a) {
        RobocodeLearningState state = (RobocodeLearningState) s;
        RobocodeLearningState newState = new RobocodeLearningState(this);
        RobocodeLearningAction action = (RobocodeLearningAction)a;
        newState.setDistanceToWall(state.getDistanceToWall() + action.getMovement());
        printDebug("New state:" + newState + " for old:" + state + " and action:" + action);
        return newState;
    }

    @Override
    public IState defaultInitialState() {
        return new RobocodeLearningState(this);
    }

    @Override
    public double getReward(IState s1, IState s2, IAction a) {
        RobocodeLearningState oldState = (RobocodeLearningState) s1;
        RobocodeLearningState newState = (RobocodeLearningState) s2;
        printDebug("Reward for:" + oldState + "|" + newState);
        return newState.getDistanceToWall() < oldState.getDistanceToWall() ? 1 : -1;
    }

    @Override
    public boolean isFinal(IState s) {
        RobocodeLearningState state = (RobocodeLearningState) s;
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
