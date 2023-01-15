package com.fastcat.assemble.abstrcts;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.utils.RandomXC;

public class AbstractGame {

    public static String seed;
    public static long seedLong;

    public static RandomXC publicRandom;
    public static RandomXC cardRandom;
    public static RandomXC mapRandom;
    public static RandomXC itemRandom;
    public static RandomXC charRandom;
    public static RandomXC roomRandom;
    public static RandomXC shopRandom;
    public static RandomXC diceRandom;
    public static RandomXC battleRandom;

    public static AbstractCharacter[] chars;
    public static Array<AbstractDice> dices;
    public static Array<AbstractCard> cards;
    public static Array<AbstractItem> items;


    public AbstractGame() {
        dices = new Array<>();
        cards = new Array<>();
        items = new Array<>();
        chars = new AbstractCharacter[12];
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
