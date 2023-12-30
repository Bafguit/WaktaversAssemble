package com.fastcat.assemble.synergies;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Competitor extends AbstractSynergy {

    private static Competitor instance;

    private Competitor() {
        super("Competitor");
    }

    public AbstractMember getWinner(Array<AbstractMember> chars) {
        return chars.get(WakTower.game.battleRandom.random(0, chars.size - 1));
    }

    public int repeatAmount() {
        if(grade == 1) return 2;
        else if(grade == 2) return 3;
        else if(grade == 3) return 5;
        else if(grade == 4) return 8;
        else return 1;
    }

    public static Competitor getInstance() {
        if(instance == null) {
            instance = new Competitor();
        }
        return instance;
    }
}