package pl.edu.agh.robocode.bot.state.helper;

import java.io.Serializable;

public class RoboState implements Serializable {
    Integer nr;

    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }
}
