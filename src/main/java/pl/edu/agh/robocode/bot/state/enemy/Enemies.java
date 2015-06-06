package pl.edu.agh.robocode.bot.state.enemy;

import pl.edu.agh.robocode.exception.NullValueException;

import java.util.HashMap;
import java.util.Map;

public class Enemies {

    private Map<String, Enemy> enemies = new HashMap<String, Enemy>();

    public void addEnemy(String name, Enemy enemy) {
        enemies.put(name, enemy);
    }

    public void addAll(Enemies enemies) {
        this.enemies.putAll(enemies.enemies);
    }

    public void clear() {
        enemies.clear();
    }

    public Enemy getNearest() {
        Enemy nearest = null;
        for (Enemy enemy : enemies.values()) {
            if (nearest == null) {
                nearest = enemy;
            }

            if (enemy.getDistance() < nearest.getDistance()) {
                nearest = enemy;
            }
        }

        if (nearest == null) {
            throw new NullValueException(Enemies.class + "null nearest enemy");
        }

        return nearest;
    }

    public Map<String, Enemy> getEnemies() {
        return enemies;
    }

}
