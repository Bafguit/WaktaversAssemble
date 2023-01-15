package com.fastcat.assemble.abstrcts;

public abstract class AbstractCard {

    //local static
    public String id;
    public String name;
    public String desc;
    public int baseValue = -1;
    public int upValue = 0;
    public int baseValue2 = -1;
    public int upValue2 = 0;

    //temporary
    public int value = -1;
    public int value2 = -1;

    public AbstractCard() {

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
}
