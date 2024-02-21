package com.fastcat.assemble.relics;

import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.actions.DrawCardAction;
import com.fastcat.assemble.handlers.ActionHandler;

public class CommonRelic7 extends AbstractRelic {

    public CommonRelic7() {
        super("CommonRelic7");
    }
    
    @Override
    public void startOfTurn(boolean isPlayer) {
        if(isPlayer) ActionHandler.next(new DrawCardAction(1));
    }
}
