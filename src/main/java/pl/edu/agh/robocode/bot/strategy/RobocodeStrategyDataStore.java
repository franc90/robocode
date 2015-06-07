package pl.edu.agh.robocode.bot.strategy;

import pl.edu.agh.robocode.properties.EnvironmentProperties;

public interface RobocodeStrategyDataStore<T extends RobocodeStrategy> {

    void save(T strategy);

    T load(EnvironmentProperties properties);
}
