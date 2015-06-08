package pl.edu.agh.robocode.learning.action.creators;

import pl.edu.agh.robocode.learning.action.ActionSpecification;
import pl.edu.agh.robocode.learning.action.RobocodeLearningAction;

import java.util.EnumSet;

class CloseDistanceActionSetCreator extends AbstractActionSetCreatorChainElement implements ActionSetCreator {


    public EnumSet<RobocodeLearningAction> createActionSet(ActionSpecification specification) {
        EnumSet<RobocodeLearningAction> actions = EnumSet.allOf(RobocodeLearningAction.class);
        if (specification.isWallToClose())
            actions.remove(RobocodeLearningAction.forDirection(specification.getWallDirection()));

        if (specification.isEnemyToClose())
            actions.remove(RobocodeLearningAction.forDirection(specification.getEnemyDirection()));

        return actions;
    }

    public boolean isApplicable(ActionSpecification specification) {
        return specification.isWallToClose() || specification.isEnemyToClose();
    }
}
