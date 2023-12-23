package com.fastcat.assemble.synergies;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.actions.GainBlockAction;
import com.fastcat.assemble.handlers.ActionHandler;

public class Machinary extends AbstractSynergy {

    private static Machinary instance;

    private Machinary() {
        super("Machinary");
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer && memberCount > 0) {
            int m = getMachinaryNumber();
            for(int i = 0; i < memberCount; i++) {
                boolean b = WakTower.game.battleRandom.randomBoolean();
                if(b) ActionHandler.next(new GainBlockAction(WakTower.game.player, m, true));
                //todo else random damage (fast)
            }
        }
    }

    public int getMachinaryNumber() {
        if(grade == 0) return 2;
        else if(grade == 1) return 3;
        else if(grade == 2) return 5;
        else if(grade == 3) return 8;
        else return 0;
    }

    public static Machinary getInstance() {
        if(instance == null) {
            instance = new Machinary();
        }
        return instance;
    }
}