package pl.edu.agh.robocode.learning.action.creators;

import pl.edu.agh.robocode.learning.action.ActionSpecification;
import pl.edu.agh.robocode.learning.action.RobocodeLearningAction;

import java.util.EnumSet;

public interface ActionSetCreator {

    EnumSet<RobocodeLearningAction> createActionSet(ActionSpecification specification);

}
