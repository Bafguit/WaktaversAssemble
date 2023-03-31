package com.fastcat.assemble.abstrcts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.handlers.FileHandler;

public abstract class AbstractDice {

    public String id;
    public String name;
    public String desc;
    public DiceRarity rarity;
    public int counter = -1;
    public Sprite img;

    protected AbstractSkill[] skills = new AbstractSkill[6];
    protected int number = -1;

    public AbstractDice(String id, DiceRarity rarity) {
        this.id = id;
        this.rarity = rarity;
        img = FileHandler.dice.get(id);
        defineSkill();
        reset();
    }

    public void roll() {
        for(AbstractSkill s : skills) {
            s.coolDown(1);
        }
        number = MousseAdventure.game.diceRandom.random(0, 5);
    }

    public final void use() {
        useDice();
    }

    protected void useDice() {

    }

    public boolean canUse() {
        return number >= 0 && getSkill().canUse();
    }

    public void beforeRoll() {}

    public void afterRoll() {}

    public final int getNumber() {
        return number;
    }

    public final AbstractSkill getSkill() {
        return skills[number];
    }

    public void reset() {
        number = -1;
        for(AbstractSkill s : skills) {
            s.resetAttribute();
        }
    }

    protected abstract void defineSkill();

    public enum DiceRarity {
        BASIC, NORMAL, ENEMY
    }
}
