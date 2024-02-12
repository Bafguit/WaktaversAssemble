package com.fastcat.assemble.status;

import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.actions.RemoveStatusAction;
import com.fastcat.assemble.handlers.ActionHandler;

public class DecreaseBlockStatus extends AbstractStatus {

    private boolean fromEnemy;

    public DecreaseBlockStatus(int amount, boolean fromEnemy) {
        super("DecreaseBlock");
        this.amount = amount;
        this.fromEnemy = fromEnemy;
    }
    
    @Override
    public int calculateDef(int amount) {
        return amount - this.amount;
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(!isPlayer) {
            if(fromEnemy) fromEnemy = false;
            else ActionHandler.set(new RemoveStatusAction(owner, this));
        }
    }
}
