package com.fastcat.assemble.status;

import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.actions.RemoveStatusAction;
import com.fastcat.assemble.handlers.ActionHandler;

public class FlawStatus extends AbstractStatus {

    public FlawStatus(int amount) {
        super("Flaw");
        this.amount = amount;
    }
    
    @Override
    public int onGainBlock(int amount) {
        ActionHandler.set(new RemoveStatusAction(owner, this));
        return amount - this.amount;
    }
}
