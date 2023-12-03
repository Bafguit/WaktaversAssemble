package com.fastcat.assemble.synergies;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Kiddo extends AbstractSynergy {

    private static Kiddo instance;

    private Kiddo() {
        super("Kiddo");
    }

    public boolean isEvaded() {
        int e = WakTower.game.battleRandom.random(0, 99);
        int m = 5 + (15 * grade);
        return e < m;
    }

    public static Kiddo getInstance() {
        if(instance == null) {
            instance = new Kiddo();
        }
        return instance;
    }
}