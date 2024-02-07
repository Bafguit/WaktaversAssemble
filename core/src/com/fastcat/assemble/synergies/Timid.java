package com.fastcat.assemble.synergies;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.actions.GainBarrierAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.utils.TargetType;

public class Timid extends AbstractSynergy {

    private static Timid instance;

    private Timid() {
        super("Timid");
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        super.endOfTurn(isPlayer);
        if(grade > 0) {
            flash();
            ActionHandler.next(new GainBarrierAction(TargetType.NONE, grade == 1 ? 3 : grade == 2 ? 8 : 15 , true).setSynergy(this));
        }
    }

    public static Timid getInstance() {
        if(instance == null) {
            instance = new Timid();
        }
        return instance;
    }
}