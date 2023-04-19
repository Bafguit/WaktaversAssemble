package com.fastcat.assemble.abstracts;

public abstract class AbstractCard implements Cloneable {

    //local static
    public String id;
    public String name;
    public String desc;
    public CardRarity rarity;
    public int baseValue = -1;
    public int upValue = 0;
    public int baseValue2 = -1;
    public int upValue2 = 0;

    //temporary
    public int value = -1;
    public int value2 = -1;

    public AbstractCard(String id, CardRarity rarity) {
        this.id = id;
        this.rarity = rarity;
    }

    protected void setBaseValue(int v, int up) {
        baseValue = value = v;
        upValue = up;
    }

    protected void setBaseValue(int v) {
        baseValue = value = v;
        upValue = 0;
    }

    protected void setBaseValue2(int v, int up) {
        baseValue2 = value2 = v;
        upValue2 = up;
    }

    protected void setBaseValue2(int v) {
        baseValue2 = value2 = v;
        upValue2 = 0;
    }

    public final void use() {
        useCard();
    }

    protected abstract void useCard();

    public final AbstractCard upgrade() {
        upgradeCard();
        return this;
    }

    protected void upgradeCard() {

    }

    public boolean canUse() {
        return true;
    }

    @Override
    public final AbstractCard clone() {
        try {
            return (AbstractCard) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public enum CardRarity {
        BASIC, COMMON, RARE, LEGEND, EVENT
    }

    public enum CardTarget {
        DICE, CHARACTER, ALL_DICE, ALL_CHARACTER, NONE
    }
}
