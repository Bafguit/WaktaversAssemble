package com.fastcat.assemble.synergies;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Competitor extends AbstractSynergy {

    private static Competitor instance;
    public AbstractMember winner;

    private Competitor() {
        super("Competitor");
    }

    private AbstractMember getWinner(Array<AbstractMember> chars) {
        return chars.get(WakTower.game.battleRandom.random(0, chars.size - 1));
    }

    @Override
    public int repeatAmount(AbstractMember m) {
        if(m == winner) {
            if(grade == 1) return 2;
            else if(grade == 2) return 3;
            else if(grade == 3) return 5;
            else if(grade == 4) return 8;
        }
        return 1;
    }

    @Override
    public void onSummon(AbstractMember m) {
        if(grade > 0 && m.hasSynergy(this)) {
            winner = getWinner(members);
        }
    }

    public static Competitor getInstance() {
        if(instance == null) {
            instance = new Competitor();
        }
        return instance;
    }
}