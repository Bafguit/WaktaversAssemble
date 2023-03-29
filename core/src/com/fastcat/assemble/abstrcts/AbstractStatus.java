package com.fastcat.assemble.abstrcts;

public abstract class AbstractStatus {

    public String id;
    public AbstractEntity owner;
    public StatusType type;
    public int amount;
    public int turnLeft = -1;

    public AbstractStatus(String id, StatusType type) {
        this.id = id;
        this.type = type;
    }

    public final void apply(int amount, int turn) {
        this.amount = Math.max(this.amount, amount);
        turnLeft = Math.max(turnLeft, turn);
        onApply();
    }

    public void onInitial() {

    }

    protected void onApply() {

    }

    public int increaseAttack() {
        return 0;
    }

    public int addFixedAttack() {
        return 0;
    }

    public int addAttack() {
        return 0;
    }

    public int increaseDefense() {
        return 0;
    }

    public int addFixedDefense() {
        return 0;
    }

    public int addDefense() {
        return 0;
    }

    public int increaseMagicRes() {
        return 0;
    }

    public int addFixedMagicRes() {
        return 0;
    }

    public int addMagicRes() {
        return 0;
    }

    public void endOfRound() {
        if(turnLeft > 0) {
            turnLeft--;
            if(turnLeft == 0) {
                remove();
            }
        }
    }

    public void remove() {
        owner.removeStatus(this);
    }

    public enum StatusType {
        BUFF, DEBUFF, CC, STATIC
    }
}
