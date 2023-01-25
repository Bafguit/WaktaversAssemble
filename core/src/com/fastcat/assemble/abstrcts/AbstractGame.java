package com.fastcat.assemble.abstrcts;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.fastcat.assemble.utils.FastCatUtils;
import com.fastcat.assemble.utils.RandomXC;

public class AbstractGame {

    public String seed;
    public long seedLong;

    public RandomXC publicRandom;
    public RandomXC cardRandom;
    public RandomXC mapRandom;
    public RandomXC itemRandom;
    public RandomXC charRandom;
    public RandomXC roomRandom;
    public RandomXC shopRandom;
    public RandomXC diceRandom;
    public RandomXC battleRandom;

    public Array<AbstractEntity> chars;
    public Array<AbstractDice> dices;
    public Array<AbstractCard> deck;
    public Array<AbstractItem> items;

    public AbstractBattle battle;

    public AbstractFloor[] floors;
    public int floorNum;

    public AbstractGame() {
        dices = new Array<>();
        deck = new Array<>();
        items = new Array<>();
        chars = new Array<>();
        seed = generateRandomSeed();
        seedLong = seedToLong(seed);
        mapRandom = new RandomXC(seedLong);
        cardRandom = new RandomXC(seedLong);
        itemRandom = new RandomXC(seedLong);
        charRandom = new RandomXC(seedLong);
        roomRandom = new RandomXC(seedLong);
        shopRandom = new RandomXC(seedLong);
        diceRandom = new RandomXC(seedLong);
        publicRandom = new RandomXC(seedLong);
        battleRandom = new RandomXC(seedLong);
    }

    public AbstractFloor currentFloor() {
        return floors[floorNum];
    }

    private static String generateRandomSeed() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            boolean t = MathUtils.randomBoolean();
            char c;
            if (t) {
                c = (char) MathUtils.random(48, 57);
            } else {
                c = (char) MathUtils.random(65, 90);
            }
            s.append(c);
        }
        return s.toString();
    }

    public static long seedToLong(String s) {
        char[] ca = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : ca) {
            sb.append((int) c);
        }
        return Long.parseLong(sb.toString());
    }
}
