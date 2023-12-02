package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.utils.FastCatUtils;

import java.util.LinkedList;

public abstract class AbstractBattle implements Cloneable {

    public BattleType type;
    public BattlePhase phase;

    public Array<AbstractChar.Synergy> synergy;

    public Array<AbstractEnemy> enemies;
    public Array<AbstractChar> chars;

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
        turnDraw(1);
    }

    public void turnDraw(int amount) {
        if(drawPile.size >= amount) {
            for(int i = 0; i < amount; i++) {
                hand.add(drawPile.removeFirst());
            }
        } else {
            if(drawPile.size > 0) {
                int s = drawPile.size;
                for(int i = 0; i < s; i++) {
                    hand.add(drawPile.removeFirst());
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
