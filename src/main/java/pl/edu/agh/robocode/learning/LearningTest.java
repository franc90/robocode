package pl.edu.agh.robocode.learning;

import piqle.agents.LoneAgent;
import piqle.algorithms.QLearningSelector;
import piqle.referees.OnePlayerReferee;

class LearningTest {

    public static void main(String[] argv) {
        RobocodeLearningEnvironment environment = new RobocodeLearningEnvironment();
        QLearningSelector algo = new QLearningSelector();
        algo.setGamma(1.00);
        double epsi=0.5;
        algo.setEpsilon(epsi);
        LoneAgent agent = new LoneAgent(environment, algo);
        OnePlayerReferee referee = new OnePlayerReferee(agent);
        referee.setMaxIter(100);
        double total = 0.0;
        double perEpisode = 0.0;
        for (int i = 0; i < 10000; i++) {
            referee.episode(new RobocodeLearningState(environment));
            double reward = referee.getRewardForEpisode();
            total += reward;
            perEpisode += reward;
            if ((i % 1000 == 0) && (i != 0)) {
                System.out.println("Episodes " + i + " Average reward " + total / i + " Av. Reward for last 10000 episodes " + perEpisode / 1000.0);
                perEpisode = 0.0;
            }
            epsi*=0.99999;
            algo.setEpsilon(epsi);
        }
    }
}
