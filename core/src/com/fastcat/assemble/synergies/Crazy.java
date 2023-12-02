package com.fastcat.assemble.abstracts;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Crazy extends AbstractSynergy {

    private static Crazy instance;

    private Crazy() {
        super("Crazy");
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        flash();
        ActionHandler.next(new DrawCardAction(grade, true));
    }

    public static Crazy getInstance() {
        if(instance == null) {
            instance = new Crazy();
        }
        return instance;
    }
}