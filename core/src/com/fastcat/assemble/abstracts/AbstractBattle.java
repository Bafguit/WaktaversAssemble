package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.screens.battle.EnemyButton;
import com.fastcat.assemble.screens.battle.TileSquare;
import com.fastcat.assemble.utils.FastCatUtils;

public abstract class AbstractBattle implements Cloneable {

    public BattleType type;

    public Array<AbstractEnemy> enemies;
    public Array<AbstractDice> dices;

    public TileSquare[][] map;

    public Queue<AbstractDice> drawPile;
    public Array<AbstractDice> discardPile;
    public Array<AbstractDice> exhaustPile;
    public Array<AbstractDice> hand;

    public int wSize;
    public int hSize;

    public AbstractBattle(BattleType type, int w, int h) {
        this.type = type;
        wSize = w;
        hSize = h;
        setDefault();
    }

    public void prepareDeck() {
        enemies = new Array<>();
        dices = new Array<>();
        drawPile = new Queue<>();
        exhaustPile = new Array<>();
        discardPile = new Array<>();
        hand = new Array<>();
        FastCatUtils.staticShuffle(dices, MousseAdventure.game.publicRandom, AbstractDice.class);
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
                FastCatUtils.staticShuffle(discardPile, MousseAdventure.game.battleRandom, AbstractDice.class);
                for(AbstractDice c : discardPile) {
                    drawPile.addLast(c);
                }
                discardPile.clear();
            }
        }
    }

    protected void setDefault() {
        map = new TileSquare[wSize][hSize];
        enemies = new Array<>();
        defineMap();
        setEnemies();
    }

    public abstract void setEnemies();

    protected void defineMap() {
        for(int i = 0; i < wSize; i++) {
            for(int j = 0; j < hSize; j++) {
                map[i][j] = new TileSquare(TileSquare.TileStatus.NORMAL, i, j);
            }
        }
    }

    protected void addEnemy(AbstractEnemy e, int x, int y) {
        EnemyButton ui = new EnemyButton(e);
        TileSquare t = map[x][y];
        t.enemy = ui;
        t.status = TileSquare.TileStatus.ENTITY;
        ui.tile = t;
        ui.pos.set(x, y);
        enemies.add(e);
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
}
