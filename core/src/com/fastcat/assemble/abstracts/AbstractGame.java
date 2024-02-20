package com.fastcat.assemble.abstracts;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.SynergyHandler;
import com.fastcat.assemble.members.AmadeusChoi;
import com.fastcat.assemble.members.Angel;
import com.fastcat.assemble.members.Bujeong;
import com.fastcat.assemble.members.Bulgom;
import com.fastcat.assemble.members.BusinessKim;
import com.fastcat.assemble.members.Butterus;
import com.fastcat.assemble.members.CallyCarly;
import com.fastcat.assemble.members.Charlotte;
import com.fastcat.assemble.members.Chouloky;
import com.fastcat.assemble.members.Chunsik;
import com.fastcat.assemble.members.Dandap;
import com.fastcat.assemble.members.Dokohyeji;
import com.fastcat.assemble.members.Dopamine;
import com.fastcat.assemble.members.Duksu;
import com.fastcat.assemble.members.Freeter;
import com.fastcat.assemble.members.Gilbert;
import com.fastcat.assemble.members.Gosegu;
import com.fastcat.assemble.members.Haku;
import com.fastcat.assemble.members.Hikiking;
import com.fastcat.assemble.members.Hodd;
import com.fastcat.assemble.members.Ine;
import com.fastcat.assemble.members.Jentoo;
import com.fastcat.assemble.members.Jingburger;
import com.fastcat.assemble.members.Jinhe;
import com.fastcat.assemble.members.Jururu;
import com.fastcat.assemble.members.Kimchimandu;
import com.fastcat.assemble.members.Kkekki;
import com.fastcat.assemble.members.Kreaze;
import com.fastcat.assemble.members.Lilpa;
import com.fastcat.assemble.members.Ninnin;
import com.fastcat.assemble.members.Pungsin;
import com.fastcat.assemble.members.Roentgenium;
import com.fastcat.assemble.members.Rusuk;
import com.fastcat.assemble.members.Seyong;
import com.fastcat.assemble.members.Sirian;
import com.fastcat.assemble.members.Soosemi;
import com.fastcat.assemble.members.Sophia;
import com.fastcat.assemble.members.Sullivan;
import com.fastcat.assemble.members.Valentine;
import com.fastcat.assemble.members.Victory;
import com.fastcat.assemble.members.Viichan;
import com.fastcat.assemble.members.Wakgood;
import com.fastcat.assemble.members.Wakpago;
import com.fastcat.assemble.members.Yungter;
import com.fastcat.assemble.relics.GuardianRelic;
import com.fastcat.assemble.utils.RandomXC;

public class AbstractGame {

    public final String seed;
    public final long seedLong;

    public final ActionHandler actionHandler;

    public final AbstractBattle battle;
    public final RandomXC publicRandom;
    public final RandomXC cardRandom;
    public final RandomXC mapRandom;
    public final RandomXC itemRandom;
    public final RandomXC charRandom;
    public final RandomXC roomRandom;
    public final RandomXC shopRandom;
    public final RandomXC diceRandom;
    public final RandomXC battleRandom;

    public Array<AbstractMember> deck;
    public LinkedList<AbstractRelic> relics;

    public AbstractRoom currentRoom;
    public int gold;
    public int stageNum, stageMax, drawAmount, maxHand, memberLimit;

    public AbstractGame() {
        relics = new LinkedList<>();
        deck = getStartDeck();
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
        stageNum = 0;
        stageMax = 20;
        drawAmount = 5;
        maxHand = 10;
        memberLimit = 6;

        for(JsonValue v : FileHandler.getInstance().jsonMap.get("synergy")) {
            SynergyHandler.getSynergyInstance(v.name).resetAll();
        }

        currentRoom = AbstractRoom.getRoom(mapRandom, 0);
        battle = new AbstractBattle(this, currentRoom);

        gainRelic(new GuardianRelic());
    }

    public void update() {
        actionHandler.update();
    }

    public void render(SpriteBatch sb) {
        actionHandler.render(sb);
    }

    public Array<AbstractMember> getStartDeck() {
        Array<AbstractMember> a = new Array<>();
        
        a.add(new Wakgood());
        a.add(new Angel());
        a.add(new Ine());
        a.add(new Jingburger());
        a.add(new Lilpa());
        a.add(new Jururu());
        a.add(new Gosegu());
        a.add(new Viichan());
        a.add(new Kimchimandu());
        a.add(new Freeter());
        a.add(new BusinessKim());
        a.add(new Haku());
        a.add(new Hikiking());
        a.add(new Sullivan());
        a.add(new Rusuk());
        a.add(new Gilbert());
        a.add(new Duksu());
        a.add(new Chunsik());
        a.add(new Bujeong());
        a.add(new Roentgenium());
        a.add(new Ninnin());
        a.add(new Kkekki());
        a.add(new Hodd());
        a.add(new Bulgom());
        a.add(new AmadeusChoi());
        a.add(new Seyong());
        a.add(new Sophia());
        a.add(new Victory());
        a.add(new Jinhe());
        a.add(new Jentoo());
        a.add(new Wakpago());
        a.add(new Yungter());
        a.add(new Dopamine());
        a.add(new CallyCarly());
        a.add(new Dokohyeji());
        a.add(new Pungsin());
        a.add(new Dandap());
        a.add(new Soosemi());
        a.add(new Sirian());
        a.add(new Kreaze());
        a.add(new Chouloky());
        a.add(new Valentine());
        a.add(new Charlotte());
        a.add(new Butterus());

        return a;
    }

    public void nextRoom() {
        stageNum++;
        if(stageNum == stageMax) {
            ending();
        } else if(stageNum < stageMax) {
            currentRoom = AbstractRoom.getRoom(mapRandom, stageNum);
        }
    }

    public void ending() {}

    public void gainGold(int gold) {
        this.gold += gold;
        if(gold < 0) this.gold = 0;
    }

    public void gainRelic(AbstractRelic relic) {
        relics.add(relic);
        relic.onGain();
    }

    public boolean removeRelic(AbstractRelic relic) {
        if(relics.remove(relic)) {
            relic.onLose();
            return true;
        } else return removeRelic(relic.id);
    }

    public boolean removeRelic(String id) {
        Iterator<AbstractRelic> itr = relics.iterator();
        while (itr.hasNext()) {
            AbstractRelic r = itr.next();
            if(r.id.equals(id)) {
                itr.remove();
                r.onLose();
                return true;
            }
        }
        return false;
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
