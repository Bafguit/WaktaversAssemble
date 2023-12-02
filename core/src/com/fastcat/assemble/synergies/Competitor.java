package com.fastcat.assemble.abstracts;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Competitor extends AbstractSynergy {

    private static Competitor instance;

    private Competitor() {
        super("Competitor");
    }

    public AbstractChar getWinner(Array<AbstractChar> chars) {
        return chars.get(WakTower.game.battleRandom.random(0, chars.size() - 1));
    }

    public static Competitor getInstance() {
        if(instance == null) {
            instance = new Competitor();
        }
        return instance;
    }
}