package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.utils.FastCatUtils;

import java.util.LinkedList;

public abstract class AbstractBattle implements Cloneable {

    public BattleType type;
    public BattlePhase phase;

    public Array<AbstractSynergy> synergy;

    public Array<AbstractEnemy> enemies;
    public Array<AbstractMember> members;

    public Queue<AbstractCard> drawPile;
    public Array<AbstractCard> discardPile;
    public Array<AbstractCard> exhaustPile;
    public LinkedList<AbstractCard> hand;

    public int wSize;
    public int hSize;

    public AbstractBattle(BattleType type, int w, int h) {
        this.type = type;
        wSize = w;
        hSize = h;
        phase = BattlePhase.battleStart;
    }

    public void turnDraw() {
        draw(WakTower.game.drawAmount);
    }

    public void draw(int amount) {
        if(drawPile.size >= amount) {
            for(int i = 0; i < amount; i++) {
                hand.add(drawPile.removeFirst());
                if(hand.size() == WakTower.game.maxHand) break;
            }
        } else {
            if(drawPile.size > 0) {
                int s = drawPile.size;
                for(int i = 0; i < s; i++) {
                    hand.add(drawPile.removeFirst());
                    if(hand.size() == WakTower.game.maxHand) break;
                }
                amount -= s;
            }
            FastCatUtils.staticShuffle(discardPile, WakTower.game.battleRandom, AbstractCard.class);
            for(AbstractCard c : discardPile) {
                drawPile.addLast(c);
            }
            for(int i = 0; i < amount; i++) {
                hand.add(drawPile.removeFirst());
            }
            discardPile.clear();
        }
    }

    public boolean isPlayerTurn() {
        return WakTower.game.battle.phase == BattlePhase.playerTurn;
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
        battleStart, roundStart, playerTurn, intermission, enemyTurn, roundEnd
    }
}
