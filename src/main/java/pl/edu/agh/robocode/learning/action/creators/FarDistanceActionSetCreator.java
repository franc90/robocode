package pl.edu.agh.robocode.learning.action.creators;

import pl.edu.agh.robocode.learning.action.ActionSpecification;
import pl.edu.agh.robocode.learning.action.RobocodeLearningAction;

import java.util.EnumSet;

class FarDistanceActionSetCreator extends AbstractActionSetCreatorChainElement implements ActionSetCreator {

    public EnumSet<RobocodeLearningAction> createActionSet(ActionSpecification specification) {
        EnumSet<RobocodeLearningAction> actions = EnumSet.noneOf(RobocodeLearningAction.class);

        if (specification.isWallToFar() || specification.isEnemyToFar())
            actions.add(RobocodeLearningAction.forDirection(specification.getRobotDirection()));

        return actions;
    }

    public boolean isApplicable(ActionSpecification specification) {
        return specification.isEnemyToFar() || specification.isWallToFar();
    }
}
