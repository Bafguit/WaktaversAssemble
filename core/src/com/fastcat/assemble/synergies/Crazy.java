package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.actions.DrawCardAction;
import com.fastcat.assemble.handlers.ActionHandler;

public class Crazy extends AbstractSynergy {

    private static Crazy instance;

    private Crazy() {
        super("Crazy");
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        super.endOfTurn(isPlayer);
        if(grade > 0) {
            flash();
            ActionHandler.next(new DrawCardAction(grade));
        }
    }

    public static Crazy getInstance() {
        if(instance == null) {
            instance = new Crazy();
        }
        return instance;
    }
}