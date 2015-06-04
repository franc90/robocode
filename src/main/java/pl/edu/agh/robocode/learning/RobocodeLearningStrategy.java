package pl.edu.agh.robocode.learning;

import piqle.agents.LoneAgent;
import piqle.algorithms.QLearningSelector;
import pl.edu.agh.robocode.bots.*;
import pl.edu.agh.robocode.bots.RobocodeState;
import pl.edu.agh.robocode.motion.MotionAction;

public class RobocodeLearningStrategy implements RobocodeStrategy {

    private final LoneAgent agent;

    public RobocodeLearningStrategy() {
        QLearningSelector algo = new QLearningSelector();
        this.agent = new LoneAgent(new RobocodeLearningEnvironment(), algo);
    }

    @Override
    public MotionAction getAction(RobocodeState state) {
        return null;
    }

    @Override
    public void newRound() {
        agent.newEpisode();
    }
}
