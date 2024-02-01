package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.GroupHandler;
import com.fastcat.assemble.relics.TestRelic;
import com.fastcat.assemble.utils.RandomXC;

public class AbstractGame {

    public String seed;
    public long seedLong;

    public ActionHandler actionHandler;

    public RandomXC publicRandom;
    public RandomXC cardRandom;
    public RandomXC mapRandom;
    public RandomXC itemRandom;
    public RandomXC charRandom;
    public RandomXC roomRandom;
    public RandomXC shopRandom;
    public RandomXC diceRandom;
    public RandomXC battleRandom;

    public AbstractPlayer player;
    public Array<AbstractMember> deck;
    public Array<AbstractRelic> relics;

    public AbstractRoom[] rooms;

    public AbstractBattle battle;
    public int gold;
    public int floorNum, floorMax, drawAmount, maxHand, memberLimit, energyStart, energyCharge, energyMax;

    public AbstractGame() {
        relics = new Array<>();
        player = new AbstractPlayer();
        deck = player.getStartDeck();
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
        actionHandler = new ActionHandler();
        gold = 100;
        floorNum = 0;
        floorMax = 30;
        drawAmount = 5;
        maxHand = 10;
        memberLimit = 8;
        energyStart = 3;
        energyCharge = 1;
        energyMax = 3;

        relics.add(new TestRelic());
    }

    public void update() {
        actionHandler.update();
    }

    public void render(SpriteBatch sb) {
        actionHandler.render(sb);
    }

    public void nextRoom() {
        if(floorNum % 10 == 9) {
            rooms = new AbstractRoom[1];
            rooms[0] = new AbstractRoom(GroupHandler.getBoss((++floorNum) / 10));
        } else if(floorNum == floorMax) {
            ending();
        } else {
            //int f = floorNum / 10 + 1;
            //todo 방 무작위 생성
            //rooms = new AbstractRoom(GroupHandler.monsterGroup.get("normal_" + f + "_" + WakTower.game.mapRandom.random(0, 9)));
        }
    }

    public void ending() {}

    public void gainGold(int gold) {
        this.gold += gold;
        if(gold < 0) this.gold = 0;
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
