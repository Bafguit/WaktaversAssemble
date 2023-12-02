package com.fastcat.assemble.abstracts;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Guardian extends AbstractSynergy {

    private static Guardian instance;

    private Guardian() {
        super("Guardian");
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        flash();
        ActionHandler.next(new GainBlockAction(grade == 1 ? 5 : grade == 2 ? 12 : grade == 3 ? 25 : 50, true));
    }

    public static Guardian getInstance() {
        if(instance == null) {
            instance = new Guardian();
        }
        return instance;
    }
}