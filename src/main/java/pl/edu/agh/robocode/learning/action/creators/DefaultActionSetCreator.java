package pl.edu.agh.robocode.learning.action.creators;

import pl.edu.agh.robocode.learning.action.ActionSpecification;
import pl.edu.agh.robocode.learning.action.RobocodeLearningAction;

import java.util.EnumSet;

class DefaultActionSetCreator extends AbstractActionSetCreatorChainElement implements ActionSetCreator {

    public EnumSet<RobocodeLearningAction> createActionSet(ActionSpecification specification) {
        return EnumSet.allOf(RobocodeLearningAction.class);
    }

    public boolean isApplicable(ActionSpecification specification) {
        return true;
    }
}
