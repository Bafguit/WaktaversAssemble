package com.fastcat.assemble.status;

import com.fastcat.assemble.abstrcts.AbstractStatus;

public class IncreaseDefenseStatus extends AbstractStatus {

    public static final String ID = "IncreaseDefense";

    public IncreaseDefenseStatus(String hash, int amount, int turn) {
        super(ID + hash, StatusType.BUFF);
        this.amount = amount;
        this.turnLeft = turn;
    }

    @Override
    public int increaseDefense() {
        return amount;
    }
}
