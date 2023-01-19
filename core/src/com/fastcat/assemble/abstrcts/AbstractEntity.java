package com.fastcat.assemble.abstrcts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.fastcat.assemble.handlers.FileHandler;

public abstract class AbstractEntity {

    //local static
    public String id;
    public String name;
    public String desc;
    public EntityRarity rarity;
    public Sprite img;

    public int uuid;
    public int health;
    public int maxHealth;
    public int upgrade;
    public int baseAttack;
    public int baseSpell;
    public int baseValue;
    public boolean isDie;
    public boolean isDead;

    //temporary
    public int attack;
    public int spell;
    public int value;

    public AbstractEntity(String id, int health, EntityRarity rarity) {
        this.id = id;
        this.health = maxHealth = health;
        this.rarity = rarity;
        img = FileHandler.character.get(id);
    }

    public abstract String getDesc(int num);

    public abstract void useCard(int num);

    public final boolean isAlive() {
        return !isDead && !isDie;
    }

    public enum EntityRarity {
        BASIC,
        COMMON,
        RARE,
        LEGEND,
        EVENT,
        ENEMY
    }
}
