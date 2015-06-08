package pl.edu.agh.robocode.learning.action.creators;

import pl.edu.agh.robocode.learning.action.ActionSpecification;
import pl.edu.agh.robocode.learning.action.RobocodeLearningAction;

import java.util.EnumSet;

public class ActionSetCreatorChain implements ActionSetCreator {

    private final ActionSetCreatorChainElement mainCreator;

    public ActionSetCreatorChain() {
        mainCreator = new CloseDistanceActionSetCreator();
        ActionSetCreatorChainElement farDistanceCreator = new FarDistanceActionSetCreator();
        ActionSetCreatorChainElement defaultCreator = new DefaultActionSetCreator();
        mainCreator.setNextElement(farDistanceCreator);
        farDistanceCreator.setNextElement(defaultCreator);
    }

    public EnumSet<RobocodeLearningAction> createActionSet(ActionSpecification specification) {
        return mainCreator.createSetOfActions(specification);
    }

    interface ActionSetCreatorChainElement extends ActionSetCreator {

        boolean isApplicable(ActionSpecification specification);
        void setNextElement(ActionSetCreatorChainElement element);
        EnumSet<RobocodeLearningAction> createSetOfActions(ActionSpecification specification);
    }

}
