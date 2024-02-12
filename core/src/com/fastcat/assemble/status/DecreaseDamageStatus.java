package com.fastcat.assemble.status;

import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.actions.RemoveStatusAction;
import com.fastcat.assemble.handlers.ActionHandler;

public class DecreaseDamageStatus extends AbstractStatus {

    private boolean fromEnemy;

    public DecreaseDamageStatus(int amount, boolean fromEnemy) {
        super("DecreaseDamage");
        this.amount = amount;
        this.fromEnemy = fromEnemy;
    }
    
    @Override
    public int calculateAtk(int atk) {
        return atk - amount;
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(!isPlayer) {
            if(fromEnemy) fromEnemy = false;
            else ActionHandler.set(new RemoveStatusAction(owner, this));
        }
    }
}
