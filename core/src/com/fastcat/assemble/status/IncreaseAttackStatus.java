package com.fastcat.assemble.status;

import com.fastcat.assemble.abstrcts.AbstractStatus;

public class IncreaseAttackStatus extends AbstractStatus {

    public static final String ID = "IncreaseAttack";

    public IncreaseAttackStatus(String hash, int amount, int turn) {
        super(ID + hash, StatusType.BUFF);
        this.amount = amount;
        this.turnLeft = turn;
    }

    @Override
    public int increaseAttack() {
        return amount;
    }
}
