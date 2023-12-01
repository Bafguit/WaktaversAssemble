package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.StringHandler;

public abstract class AbstractCard implements Cloneable {

    public  CardData data;

    //local static
    public String id;
    public String name;
    public String desc;
    public String upDesc;
    public Sprite img;
    public CardRarity rarity;
    public CardType type;
    public int baseValue = -1;
    public int upValue = 0;
    public int baseValue2 = -1;
    public int upValue2 = 0;
    public int upgradeCount = 0;
    public int lock = 0;
    public boolean isGhost = false;
    public boolean retain = false;
    public boolean exhaust = false;
    public boolean frozen = false;
    public boolean isReturn = false;

    //temporary
    public int value = -1;
    public int value2 = -1;

    public AbstractCard(String id) {
        this.id = id;
        /*data = new CardData(id); //todo DataHandler, hash 만들기
        name = data.name;
        desc = data.desc;
        upDesc = data.upDesc;
        rarity = data.rarity;
        type = data.type;
        img = FileHandler.card.get(id);*/
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

    public String getName() {
        return name + (upgradeCount > 0 ? "+" + upgradeCount : "");
    }

    public static class CardData {
        public final String id, name, desc, upDesc;
        public final CardRarity rarity;
        public final CardType type;
        public final Sprite img;

        public CardData(String id, JsonValue json) {
            this.id = id;
            name = json.getString("name");
            desc = json.getString("desc");
            upDesc = json.getString("upDesc", desc);
            rarity = CardRarity.valueOf(json.getString("rarity"));
            type = CardType.valueOf("type");
            img = FileHandler.card.get(id);
        }
    }

    public enum CardRarity {
        BASIC, COMMON, RARE, LEGEND, EVENT
    }

    public enum CardType {
        ATTACK, SUPPORT
    }

    public enum CardTarget {
        DICE, CHARACTER, ALL_DICE, ALL_CHARACTER, NONE
    }
}
