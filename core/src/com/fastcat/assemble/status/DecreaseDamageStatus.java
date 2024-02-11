package com.fastcat.assemble.status;

import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.actions.RemoveStatusAction;
import com.fastcat.assemble.handlers.ActionHandler;

public class DecreaseDamageStatus extends AbstractStatus {

    public DecreaseDamageStatus(int amount) {
        super("DecreaseDamage");
        this.amount = amount;
    }
    
    @Override
    public int calculateAtk(int atk) {
        return atk - amount;
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(!isPlayer) ActionHandler.set(new RemoveStatusAction(owner, this));
    }
}
