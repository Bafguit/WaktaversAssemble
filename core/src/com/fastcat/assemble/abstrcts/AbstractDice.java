package com.fastcat.assemble.abstrcts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.handlers.FileHandler;

public abstract class AbstractDice {

    public String id;
    public String name;
    public String desc;
    public DiceRarity rarity;
    public Sprite img;
    public int counter = -1;

    protected TextureAtlas atlas;
    protected Array<Sprite> images;
    protected int number = 0;

    public AbstractDice(String id, DiceRarity rarity) {
        this.id = id;
        this.rarity = rarity;
        atlas = FileHandler.diceAtlas.get(id);
        images = new Array<>();
        for(int i = 0; i < atlas.getRegions().size; i++) {
            images.add(atlas.createSprite(Integer.toString(i)));
        }
        reset();
    }

    public void roll() {
        rollDice();
        img = images.get(number);
    }

    protected abstract void rollDice();

    public final int getNumber() {
        return number;
    }

    public void reset() {
        number = 0;
        img = images.get(number);
    }

    public enum DiceRarity {
        BASIC, NORMAL, RARE, LEGEND, EVENT
    }
}
