package com.fastcat.assemble.abstrcts;

public abstract class AbstractCharacter {

    //local static
    public String id;
    public String name;
    public String desc;
    public int uuid;
    public int upgrade;
    public int baseAttack;
    public int baseSpell;
    public int baseValue;

    //temporary
    public int attack;
    public int spell;
    public int value;
    public AbstractDice dice;

    public AbstractCharacter() {

    }

    public abstract String getDesc(int num);

    public abstract void useCard(int num);
}
