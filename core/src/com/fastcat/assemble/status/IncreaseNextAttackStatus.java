package com.fastcat.assemble.status;

import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.actions.RemoveStatusAction;
import com.fastcat.assemble.handlers.ActionHandler;

public class IncreaseNextAttackStatus extends AbstractStatus {

    public static final String ID = "IncreaseNextAttack";

    public IncreaseNextAttackStatus(String hash, int amount) {
        super(ID, StatusType.BUFF);
        id += hash;
        this.amount = amount;
        this.turnLeft = -1;
    }

    @Override
    public int increaseAttack() {
        return amount;
    }

    @Override
    public void onAfterAttack() {
        ActionHandler.bot(new RemoveStatusAction(owner, this, true));
    }

    @Override
    public String getDescription() {
        return exDesc[0] + amount + exDesc[1];
    }
}
