package com.fastcat.assemble.status;

import com.fastcat.assemble.abstracts.AbstractStatus;

public class IncreaseAttackStatus extends AbstractStatus {

    public static final String ID = "IncreaseAttack";

    public IncreaseAttackStatus(String hash, int amount, int turn) {
        super(ID, StatusType.BUFF);
        id += hash;
        this.amount = amount;
        this.turnLeft = turn;
    }

    @Override
    public int increaseAttack() {
        return amount;
    }

    @Override
    public String getDescription() {
        return exDesc[0] + amount + exDesc[1];
    }
}
