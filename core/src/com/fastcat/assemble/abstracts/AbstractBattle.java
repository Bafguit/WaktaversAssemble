package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.fastcat.assemble.interfaces.OnIncreaseGlobalDamage;
import com.fastcat.assemble.interfaces.OnIncreaseMemberDamage;
import com.fastcat.assemble.interfaces.OnIncreaseMemberDef;
import com.fastcat.assemble.stages.battle.BattleStage;
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

public class AbstractBattle {

    private final AbstractGame game;
    private BattleStage stage;

    public BattleType type;
    public BattlePhase phase;

    public Array<AbstractSynergy> synergy = new Array<>();

    //todo 턴 종료 시 클리어
    public LinkedList<OnIncreaseGlobalDamage> turnGlobalDamage = new LinkedList<>();
    public LinkedList<OnIncreaseMemberDamage> turnMemberDamage = new LinkedList<>();
    public LinkedList<OnIncreaseMemberDef> turnMemberDef = new LinkedList<>();

    public LinkedList<AbstractEnemy> enemies = new LinkedList<>();
    public LinkedList<AbstractMember> members = new LinkedList<>();

    public Queue<AbstractMember> drawPile = new Queue<>();
    public Array<AbstractMember> discardPile = new Array<>();
    public Array<AbstractMember> exhaustPile = new Array<>();
    public LinkedList<AbstractMember> hand = new LinkedList<>();

    public int energy;

    public AbstractBattle(AbstractGame game, AbstractRoom room) {
        this.game = game;
        Array<AbstractMember> mm = new Array<AbstractMember>(game.deck);
        FastCatUtils.arrayShuffle(mm, game.battleRandom, AbstractMember.class);
        for(AbstractMember m : mm) {
            drawPile.addLast(m.duplicate());
        }
        setEnemy(room);
        for(AbstractEnemy e : enemies) {
            e.action = e.getAction();
        }
        phase = BattlePhase.BATTLE_START;
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
        draw(game.drawAmount);
    }

    public void draw(int amount) {
        if(hand.size() < game.maxHand) {
        if(drawPile.size >= amount) {
            for(int i = 0; i < amount; i++) {
                AbstractMember m = drawPile.removeFirst();
                hand.add(m);
                m.onDrawn();
                //WakTower.battleScreen.addHand(m);
                if(hand.size() == game.maxHand) break;
            }
        } else {
            if(drawPile.size > 0) {
                int s = drawPile.size;
                for(int i = 0; i < s; i++) {
                    AbstractMember m = drawPile.removeFirst();
                    hand.add(m);
                    m.onDrawn();
                    //WakTower.battleScreen.addHand(m);
                    if(hand.size() == game.maxHand) break;
                }
                amount -= s;
            }
            FastCatUtils.arrayShuffle(discardPile, game.battleRandom, AbstractMember.class);
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

    public void discard(AbstractMember m) {

    }

    public void discardAll() {
        
    }

    public boolean isPlayerTurn() {
        return game.battle.phase == BattlePhase.PLAYER_TURN;
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
        Iterator<AbstractMember> itr = members.iterator();
        while (itr.hasNext()) {
            AbstractMember m = itr.next();
            if(!m.hasSynergy("Cat")) {
                itr.remove();
                for(AbstractSynergy s : m.synergy) {
                    s.members.remove(m);
                    s.update();
                }
                stage.discardField(m);
            }
        }
        stage.updateSynergy();
    }

    public void setEnemy(AbstractRoom room) {
        enemies.clear();
        for(AbstractEnemy e : room.enemies) {
            enemies.add(e.duplicate());
        }
    }

    public void endBattle() {
        Iterator<AbstractMember> itr = discardPile.iterator();
        while (itr.hasNext()) {
            AbstractMember m = itr.next();
            itr.remove();
            m.health = 1;
            drawPile.addLast(m);
        }
        FastCatUtils.queueShuffle(drawPile, game.battleRandom, AbstractMember.class);
    }

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
        BATTLE_START, ROUND_START, PLAYER_TURN, INTERMISSION, ENEMY_TURN, ROUND_END, BATTLE_END, REWARD
    }
}
