package com.fastcat.assemble.status;

import com.fastcat.assemble.abstracts.AbstractStatus;

public class IncreaseDefenseStatus extends AbstractStatus {

    public static final String ID = "IncreaseDefense";

    public IncreaseDefenseStatus(String hash, int amount, int turn) {
        super(ID, StatusType.BUFF);
        id += hash;
        this.amount = amount;
        this.turnLeft = turn;
    }

    @Override
    public int increaseDefense() {
        return amount;
    }

    @Override
    public String getDescription() {
        return exDesc[0] + amount + exDesc[1];
    }
}
