package pl.edu.agh.robocode.learning.action;

import piqle.environment.ActionList;
import piqle.environment.IState;
import pl.edu.agh.robocode.learning.action.creators.ActionSetCreatorChain;

import java.util.EnumSet;

public class ActionListCreatorFactory {

    private final ActionSpecification specification;
    private final IState state;
    private final ActionSetCreatorChain creatorChain;

    public static ActionListCreatorFactory forSpecificationAndState(ActionSpecification specification, IState state) {
        return new ActionListCreatorFactory(specification, state);
    }

    private ActionListCreatorFactory(ActionSpecification specification, IState state) {
        this.specification = specification;
        this.state = state;
        this.creatorChain = new ActionSetCreatorChain();
    }

    public ActionList create() {
        EnumSet<RobocodeLearningAction> actionsSet = creatorChain.createActionSet(specification);
        return fromEnumSet(actionsSet);
    }

    private ActionList fromEnumSet(EnumSet<RobocodeLearningAction> set) {
        ActionList actionList = new ActionList(state);
        for (RobocodeLearningAction robocodeLearningAction : set) {
            actionList.add(robocodeLearningAction);
        }
        return actionList;
    }


}
