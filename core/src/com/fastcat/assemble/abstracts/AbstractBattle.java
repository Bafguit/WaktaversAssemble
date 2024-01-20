package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.Array.ArrayIterator;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.actions.StartBattleAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.interfaces.OnIncreaseGlobalDamage;
import com.fastcat.assemble.interfaces.OnIncreaseMemberDamage;
import com.fastcat.assemble.interfaces.OnIncreaseMemberDef;
import com.fastcat.assemble.interfaces.OnIncreaseWakDamage;
import com.fastcat.assemble.screens.battle.BattleStage;
import com.fastcat.assemble.synergies.Badass;
import com.fastcat.assemble.synergies.Cat;
import com.fastcat.assemble.synergies.Competitor;
import com.fastcat.assemble.synergies.Crazy;
import com.fastcat.assemble.synergies.Cutey;
import com.fastcat.assemble.synergies.Doormat;
import com.fastcat.assemble.synergies.Expert;
import com.fastcat.assemble.synergies.Guardian;
import com.fastcat.assemble.synergies.Isedol;
import com.fastcat.assemble.synergies.Kiddo;
import com.fastcat.assemble.synergies.Machinary;
import com.fastcat.assemble.synergies.Magician;
import com.fastcat.assemble.synergies.MainVocal;
import com.fastcat.assemble.synergies.MindMaster;
import com.fastcat.assemble.synergies.Nobles;
import com.fastcat.assemble.synergies.Nunna;
import com.fastcat.assemble.synergies.OldMan;
import com.fastcat.assemble.synergies.Timid;
import com.fastcat.assemble.synergies.Villain;
import com.fastcat.assemble.utils.FastCatUtils;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class AbstractBattle implements Cloneable {

    private BattleStage stage;

    public BattleType type;
    public BattlePhase phase;

    public Array<AbstractSynergy> synergy = new Array<>();

    //todo 턴 종료 시 클리어
    public LinkedList<OnIncreaseGlobalDamage> turnGlobalDamage = new LinkedList<>();
    public LinkedList<OnIncreaseMemberDamage> turnMemberDamage = new LinkedList<>();
    public LinkedList<OnIncreaseMemberDef> turnMemberDef = new LinkedList<>();

    public LinkedList<AbstractEnemy> enemies = new LinkedList<>();
    public Array<AbstractMember> members = new Array<>();

    public Queue<AbstractMember> drawPile = new Queue<>();
    public Array<AbstractMember> discardPile = new Array<>();
    public Array<AbstractMember> exhaustPile = new Array<>();
    public LinkedList<AbstractMember> hand = new LinkedList<>();

    public int energy;

    public AbstractBattle(BattleType type) {
        this.type = type;
        Array<AbstractMember> mm = new Array<AbstractMember>(WakTower.game.deck);
        FastCatUtils.staticShuffle(mm, WakTower.game.battleRandom, AbstractMember.class);
        for(AbstractMember m : mm) {
            drawPile.addLast(m.cpy());
        }
        setEnemy();
        phase = BattlePhase.battleStart;
        resetSynergy();
        //ActionHandler.bot(new StartBattleAction());
    }

    public void setStage(BattleStage stage) {
        this.stage = stage;
    }

    public BattleStage getStage() {
        return stage;
    }

    public void turnDraw() {
        draw(WakTower.game.drawAmount);
    }

    public void draw(int amount) {
        if(hand.size() < WakTower.game.maxHand) {
        if(drawPile.size >= amount) {
            for(int i = 0; i < amount; i++) {
                AbstractMember m = drawPile.removeFirst();
                hand.add(m);
                m.onDrawn();
                //WakTower.battleScreen.addHand(m);
                if(hand.size() == WakTower.game.maxHand) break;
            }
        } else {
            if(drawPile.size > 0) {
                int s = drawPile.size;
                for(int i = 0; i < s; i++) {
                    AbstractMember m = drawPile.removeFirst();
                    hand.add(m);
                    m.onDrawn();
                    //WakTower.battleScreen.addHand(m);
                    if(hand.size() == WakTower.game.maxHand) break;
                }
                amount -= s;
            }
            FastCatUtils.staticShuffle(discardPile, WakTower.game.battleRandom, AbstractMember.class);
            for(AbstractMember c : discardPile) {
                drawPile.addLast(c);
            }
            discardPile.clear();
            if(drawPile.size > 0) {
                for(int i = 0; i < amount; i++) {
                    AbstractMember m = drawPile.removeFirst();
                    hand.add(m);
                    m.onDrawn();
                    //WakTower.battleScreen.addHand(m);
                }
            }
        }
        }
        if(stage != null) stage.updateHandPosition();
    }

    public boolean isPlayerTurn() {
        return WakTower.game.battle.phase == BattlePhase.playerTurn;
    }

    private void resetSynergy() {
        synergy.clear();
        synergy.add(Guardian.getInstance());
        synergy.add(Crazy.getInstance());
        synergy.add(Expert.getInstance());
        synergy.add(Magician.getInstance());
        synergy.add(Isedol.getInstance());
        synergy.add(MainVocal.getInstance());
        synergy.add(Kiddo.getInstance());
        synergy.add(OldMan.getInstance());
        synergy.add(Badass.getInstance());
        synergy.add(Competitor.getInstance());
        synergy.add(Villain.getInstance());
        synergy.add(Cat.getInstance());
        synergy.add(Cutey.getInstance());
        synergy.add(Doormat.getInstance());
        synergy.add(Machinary.getInstance());
        synergy.add(MindMaster.getInstance());
        synergy.add(Nobles.getInstance());
        synergy.add(Nunna.getInstance());
        synergy.add(Timid.getInstance());
    }

    public void clearMember() {
        ArrayIterator<AbstractMember> itr = members.iterator();
        while (itr.hasNext()) {
            AbstractMember m = itr.next();
            if(!m.id.equals("Cat")) itr.remove();
        }
    }

    protected abstract void setEnemy();

    @Override
    public AbstractBattle clone() {
        try {
            return (AbstractBattle) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public enum BattleType {
        WEAK, NORMAL, ELITE, BOSS
    }
    
    public enum BattlePhase {
        battleStart, roundStart, playerTurn, intermission, enemyTurn, roundEnd
    }
}
