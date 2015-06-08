package pl.edu.agh.robocode.learning.action.creators;

import pl.edu.agh.robocode.learning.action.ActionSpecification;
import pl.edu.agh.robocode.learning.action.RobocodeLearningAction;

import java.util.EnumSet;

abstract class AbstractActionSetCreatorChainElement implements ActionSetCreatorChain.ActionSetCreatorChainElement {

    private ActionSetCreatorChain.ActionSetCreatorChainElement next;

    public void setNextElement(ActionSetCreatorChain.ActionSetCreatorChainElement element) {
        next = element;
    }

    public EnumSet<RobocodeLearningAction> createSetOfActions(ActionSpecification specification) {
        return isApplicable(specification) ? createActionSet(specification) : next.createSetOfActions(specification);
    }
}
