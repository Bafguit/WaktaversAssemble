package com.fastcat.assemble.abstrcts;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.effects.UpColorTextEffect;
import com.fastcat.assemble.handlers.EffectHandler;
import com.fastcat.assemble.handlers.FileHandler;

import java.util.LinkedList;

public abstract class AbstractEntity {

    //local static
    public String id;
    public String name;
    public String desc;
    public EntityRarity rarity;
    public Sprite img;
    public Vector2 pos;

    public int uuid;
    public int health;
    public int baseMaxHealth;
    public int shield;
    public int baseAttack;
    public boolean isPlayer;
    public boolean isDie;
    public boolean isDead;

    public LinkedList<AbstractStatus> status = new LinkedList<>();

    //temporary
    public int attack;
    public int maxHealth;

    public AbstractEntity(String id, int health, EntityRarity rarity) {
        this.id = id;
        this.health = baseMaxHealth = health;
        this.rarity = rarity;
        img = FileHandler.character.get(id);
    }

    public final void resetAttributes() {
        health = maxHealth = baseMaxHealth;
        attack = baseAttack;
        shield = 0;
    }

    public abstract String getDesc(int num);

    public final boolean isAlive() {
        return !isDead && !isDie;
    }

    public int calculatedAttack() {
        return baseAttack;
    }

    public void takeDamage(DamageInfo info) {
        health -= info.damage;
        EffectHandler.add(new UpColorTextEffect(pos.x, pos.y, info.damage, Color.SCARLET));
    }

    public void heal(int amount) {
        health = Math.min(health + amount, maxHealth);
        EffectHandler.add(new UpColorTextEffect(pos.x, pos.y, amount, Color.CHARTREUSE));
    }

    public static class DamageInfo {
        public DamageType type;
        public int damage;

        public DamageInfo(int damage, DamageType type) {
            this.type = type;
            this.damage = damage;
        }
    }

    public enum DamageType {
        PHYSICAL, ARTS, TRUE
    }

    public enum EntityRarity {
        OPERATOR,
        NORMAL,
        ELITE,
        LEADER
    }
}
