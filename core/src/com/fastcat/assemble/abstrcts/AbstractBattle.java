package com.fastcat.assemble.abstrcts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.fastcat.assemble.MouseAdventure;
import com.fastcat.assemble.utils.FastCatUtils;

import java.util.HashMap;

public abstract class AbstractBattle {

    public AbstractEntity player;
    public Array<AbstractEnemy> enemies;
    public Array<AbstractDice> dices;

    public Queue<AbstractDice> drawPile;
    public Array<AbstractDice> discardPile;
    public Array<AbstractDice> exhaustPile;
    public Array<AbstractDice> hand;
    public int wSize = 8, hSize = 6;

    public void prepareDeck() {
        enemies = new Array<>();
        dices = new Array<>();
        drawPile = new Queue<>();
        exhaustPile = new Array<>();
        discardPile = new Array<>();
        hand = new Array<>();
        FastCatUtils.staticShuffle(dices, MouseAdventure.game.publicRandom, AbstractDice.class);
        for(AbstractDice c : dices) {
            drawPile.addLast(c);
        }
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
            } else {
                FastCatUtils.staticShuffle(discardPile, MouseAdventure.game.battleRandom, AbstractDice.class);
                for(AbstractDice c : discardPile) {
                    drawPile.addLast(c);
                }
                discardPile.clear();
            }
        }
    }

    public abstract void setEnemies();

    protected void addEnemy(AbstractEnemy e, int x, int y) {
        enemies.add(e);
        e.pos = new Vector2(x, y);
    }
}
