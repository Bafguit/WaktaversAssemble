package com.fastcat.assemble.abstrcts;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fastcat.assemble.MouseAdventure;
import com.fastcat.assemble.handlers.FileHandler;

public abstract class AbstractDice {

    public String id;
    public String name;
    public String desc;
    public DiceRarity rarity;
    public int counter = -1;

    protected TextureAtlas atlas;
    protected AbstractSkill[] skills = new AbstractSkill[7];
    protected int number = 0;

    public AbstractDice(String id, DiceRarity rarity) {
        this.id = id;
        this.rarity = rarity;
        atlas = FileHandler.diceAtlas.get(id);
        defineSkill();
        reset();
    }

    public void roll() {
        number = MouseAdventure.game.diceRandom.random(1, 6);
    }

    public final void use() {
        useDice();
    }

    protected void useDice() {

    }

    public boolean canUse() {
        return true;
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
        number = 0;
        for(AbstractSkill s : skills) {
            s.resetAttribute();
        }
    }

    protected abstract void defineSkill();

    public enum DiceRarity {
        BASIC, NORMAL, ENEMY
    }
}
