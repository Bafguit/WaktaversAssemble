package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.actions.GainBlockAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.utils.TargetType;

public class Guardian extends AbstractSynergy {

    private static Guardian instance;

    private Guardian() {
        super("Guardian");
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        super.endOfTurn(isPlayer);
        if(isPlayer && grade > 0) {
            flash();
            ActionHandler.next(new GainBlockAction(TargetType.NONE, grade == 1 ? 5 : grade == 2 ? 9 : 15, true).setSynergy(this));
        }
    }

    public static Guardian getInstance() {
        if(instance == null) {
            instance = new Guardian();
        }
        return instance;
    }
}