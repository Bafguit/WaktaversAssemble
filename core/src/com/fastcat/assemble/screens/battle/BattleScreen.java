package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.*;
import com.fastcat.assemble.dices.basic.Mousse;
import com.fastcat.assemble.utils.Vector2i;

import java.util.LinkedList;

public class BattleScreen extends AbstractScreen {

    public BattlePhase phase;
    public ResizeButton resizeButton;
    public RollDiceButton rollButton;
    public PhaseButton phaseButton;
    public TurnEndButton turnEndButton;
    public DiceButton[] dice = new DiceButton[6];
    public DirectionButton[] dirButton = new DirectionButton[4];
    public CharacterButton player;
    public AbstractSkill.SkillDir curDir = AbstractSkill.SkillDir.UP;
    public Array<TileSquare> targetTiles = new Array<>();
    public Array<EnemyButton> enemies = new Array<>();
    public TileSquare[][] tiles;
    public int wSize = 8, hSize = 6;
    public AbstractSkill dirSkill;

    public BattleScreen() {
        super(ScreenType.BASE);
        MousseAdventure.game = new AbstractGame();
        phase = BattlePhase.READY;
        resizeButton = new ResizeButton();
        rollButton = new RollDiceButton(this);
        rollButton.setPosition(320, 650);
        phaseButton = new PhaseButton(this);
        phaseButton.setPosition(320, 500);
        turnEndButton = new TurnEndButton(this);
        turnEndButton.setPosition(1640, 500);
        for(int i = 0; i < 6; i++) {
            DiceButton b = new DiceButton(this, new Mousse(), i);
            b.setPosition(460 + 200 * i, 150);
            dice[i] = b;
        }
        for(int i = 0; i < 4; i++) {
            dirButton[i] = new DirectionButton(this, AbstractSkill.SkillDir.values()[i], -10000, -10000);
        }
        player = new CharacterButton(this);
        tiles = new TileSquare[wSize][hSize];
        for (int j = 0; j < hSize; j++) {
            for(int i = 0; i < wSize; i++) {
                TileSquare t = new TileSquare(this, TileSquare.TileStatus.NORMAL, i, j);
                t.setPosition(610 + 100 * i, 390 + 100 * j);
                tiles[i][j] = t;
            }
        }
        addEnemy(new EnemyButton(this), 3, 1);
        addEnemy(new EnemyButton(this), 3, 2);
    }

    @Override
    public void update() {
        resizeButton.update();
        rollButton.update();
        phaseButton.update();
        turnEndButton.update();

        for (DiceButton b : dice) {
            b.update();
        }

        LinkedList<AbstractStatus> ss = player.character.status;
        for(int i = 0; i < ss.size(); i++) {
            StatusIcon s = ss.get(i).icon;
            s.setPosition(50 + 50 * i, 1030);
            s.update();
        }

        player.update();

        for(EnemyButton e : enemies) {
            e.update();
        }

        if(phase == BattlePhase.DIRECTION || phase == BattlePhase.MOVE) {
            float x = player.mouse.x - player.localX;
            float y = player.mouse.y - player.localY;
            double radian = Math.atan2(y, x);
            int degree = (int) (radian * 180 / Math.PI);
            if(degree > 45 && degree <= 135) {
                curDir = AbstractSkill.SkillDir.UP;
            } else if(degree > 135 || degree < -135) {
                curDir = AbstractSkill.SkillDir.LEFT;
            } else if(degree < -45) {
                curDir = AbstractSkill.SkillDir.DOWN;
            } else {
                curDir = AbstractSkill.SkillDir.RIGHT;
            }
        }

        for(DirectionButton b : dirButton) {
            b.update();
        }

        updateTileStatus();
        for(int j = 0; j < hSize; j++) {
            for(int i = 0; i < wSize; i++) {
                TileSquare t = tiles[i][j];
                t.update();
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        resizeButton.render(sb);
        rollButton.render(sb);
        phaseButton.render(sb);
        turnEndButton.render(sb);

        for(int j = 0; j < hSize; j++) {
            for(int i = 0; i < wSize; i++) {
                TileSquare t = tiles[i][j];
                t.render(sb);
            }
        }

        player.render(sb);

        for(EnemyButton e : enemies) {
            e.render(sb);
        }

        for(DirectionButton b : dirButton) {
            b.render(sb);
        }

        for(AbstractStatus s : player.character.status) {
            s.icon.render(sb);
        }

        for(DiceButton b : dice) {
            b.render(sb);
        }
    }

    public void updateTileStatus() {
        for(TileSquare[] t : tiles) {
            for(TileSquare tt : t) {
                tt.isTarget = false;
            }
        }
        if(phase == BattlePhase.DIRECTION || phase == BattlePhase.MOVE) {
            dirSkill.setTempRange(curDir);
            for (int i = dirSkill.tempRange.length - 1; i >= 0; i--) {
                Vector2i pv = new Vector2i(player.pos);
                pv.add(dirSkill.tempRange[i]);
                int x = (int) pv.x, y = (int) pv.y;
                if (x >= 0 && x < wSize && y >= 0 && y < hSize) {
                    TileSquare tile = tiles[x][y];
                    tile.isTarget = true;
                    targetTiles.add(tile);
                }
            }
        }
    }

    public void addEnemy(EnemyButton ui, int x, int y) {
        TileSquare t = tiles[x][y];
        t.enemy = ui;
        t.status = TileSquare.TileStatus.ENTITY;
        ui.tile = t;
        ui.pos.set(x, y);
        ui.entity.pos = new Vector2(t.originX, t.originY);
        ui.setPosition(t.originX, t.originY);
        enemies.add(ui);
    }

    public void rollDice() {
        for(DiceButton b : dice) {
            b.dice.roll();
        }
        phase = BattlePhase.SKILL;
    }

    public void resetDice() {
        for(DiceButton b : dice) {
            b.reset();
        }
    }

    public void resetChar() {
        player.reset();
    }

    public enum BattlePhase {
        READY,
        DEPLOY,
        DRAW,
        SKILL,
        DIRECTION,
        MOVE,
        END_TURN,
        TURN_CHANGE,
        ENEMY,
        END
    }
}
