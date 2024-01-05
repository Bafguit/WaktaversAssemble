package com.fastcat.assemble.synergies;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.actions.GainBlockAction;
import com.fastcat.assemble.handlers.ActionHandler;

public class Guardian extends AbstractSynergy {

    private static Guardian instance;

    private Guardian() {
        super("Guardian");
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        super.endOfTurn(isPlayer);
        if(grade > 0) {
            flash();
            ActionHandler.next(new GainBlockAction(WakTower.game.player, grade == 1 ? 5 : grade == 2 ? 12 : grade == 3 ? 25 : 50, true));
        }
    }

    public static Guardian getInstance() {
        if(instance == null) {
            instance = new Guardian();
        }
        return instance;
    }
}