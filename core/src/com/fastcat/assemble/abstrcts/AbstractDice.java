package com.fastcat.assemble.abstrcts;

public abstract class AbstractDice {

    public String id;
    public String name;
    public String desc;
    public int counter = -1;

    protected int number;

    public AbstractDice(String id, DiceRarity rarity) {

    }

    public void roll() {
        rollDice();
    }

    protected abstract void rollDice();

    public final int getNumber() {
        return number;
    }

    public void reset() {
        number = 0;
    }

    public enum DiceRarity {
        BASIC, NORMAL, RARE, LEGEND, EVENT
    }
}
