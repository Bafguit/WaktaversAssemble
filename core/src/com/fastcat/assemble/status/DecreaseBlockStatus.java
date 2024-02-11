package com.fastcat.assemble.status;

import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.actions.RemoveStatusAction;
import com.fastcat.assemble.handlers.ActionHandler;

public class DecreaseBlockStatus extends AbstractStatus {

    public DecreaseBlockStatus(int amount) {
        super("DecreaseBlock");
        this.amount = amount;
    }
    
    @Override
    public int calculateDef(int amount) {
        return amount - this.amount;
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(!isPlayer) ActionHandler.set(new RemoveStatusAction(owner, this));
    }
}
