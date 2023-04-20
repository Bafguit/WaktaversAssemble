package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.*;
import com.fastcat.assemble.dices.basic.Mousse;
import com.fastcat.assemble.dices.normal.Scavenger;
import com.fastcat.assemble.enemies.SarkazSniper;
import com.fastcat.assemble.enemies.SarkazWarrior;
import com.fastcat.assemble.utils.Vector2i;

import java.util.LinkedList;

public class BattleScreen extends AbstractScreen {

    public AbstractBattle battle;
    public BattlePhase phase;
    public ResizeButton resizeButton;
    public RollDiceButton rollButton;
    public PhaseButton phaseButton;
    public TurnEndButton turnEndButton;
    public Array<DiceButton> dice = new Array<>();
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
        phase = BattlePhase.READY;
        resizeButton = new ResizeButton();
        rollButton = new RollDiceButton(this);
        rollButton.setPosition(320, 650);
        phaseButton = new PhaseButton(this);
        phaseButton.setPosition(320, 500);
        turnEndButton = new TurnEndButton(this);
        turnEndButton.setPosition(1640, 500);
        {
            DiceButton b = new DiceButton(this, new Mousse(), 0);
            b.setPosition(460, 150);
            dice.add(b);
            b = new DiceButton(this, new Mousse(), 1);
            b.setPosition(460 + 200, 150);
            dice.add(b);
            b = new DiceButton(this, new Scavenger(), 2);
            b.setPosition(460 + 400, 150);
            dice.add(b);
        }
        for(int i = 0; i < 4; i++) {
            dirButton[i] = new DirectionButton(this, AbstractSkill.SkillDir.values()[i], -10000, -10000);
        }
        player = new CharacterButton(this);
        tiles = new TileSquare[wSize][hSize];
        for (int j = 0; j < hSize; j++) {
            for(int i = 0; i < wSize; i++) {
                TileSquare t = new TileSquare(this, TileSquare.TileStatus.NORMAL, i, j);
                t.setPosition(610 + 72 * i + 72 * j, 540 + 36 * i - 36 * j);
                tiles[i][j] = t;
            }
        }
    }

    public BattleScreen(AbstractBattle battle) {
        super(ScreenType.BASE);
        this.battle = battle;
        wSize = battle.wSize;
        hSize = battle.hSize;
        phase = BattlePhase.READY;
        resizeButton = new ResizeButton();
        rollButton = new RollDiceButton(this);
        rollButton.setPosition(320, 650);
        phaseButton = new PhaseButton(this);
        phaseButton.setPosition(320, 500);
        turnEndButton = new TurnEndButton(this);
        turnEndButton.setPosition(1640, 500);
        {
            DiceButton b = new DiceButton(this, new Mousse(), 0);
            b.setPosition(460, 150);
            dice.add(b);
            b = new DiceButton(this, new Mousse(), 1);
            b.setPosition(460 + 200, 150);
            dice.add(b);
            b = new DiceButton(this, new Scavenger(), 2);
            b.setPosition(460 + 400, 150);
            dice.add(b);
        }
        for(int i = 0; i < 4; i++) {
            dirButton[i] = new DirectionButton(this, AbstractSkill.SkillDir.values()[i], -10000, -10000);
        }
        player = new CharacterButton(this);
        tiles = battle.map;
        for (int j = 0; j < hSize; j++) {
            for(int i = 0; i < wSize; i++) {
                TileSquare t = tiles[i][j];
                t.screen = this;
                t.setPosition(610 + 72 * j + 72 * i, 540 + 36 * j - 36 * i);
                if(t.enemy != null) {
                    t.enemy.pos.set(i, j);
                    t.enemy.setPosition(t.originX, t.originY + 36);
                    t.enemy.entity.pos = new Vector2(t.originX, t.originY + 36);
                    enemies.add(t.enemy);
                }
            }
        }
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

        for(int j = hSize - 1; j >= 0; j--) {
            for(int i = 0; i < wSize; i++) {
                TileSquare t = tiles[i][j];
                t.render(sb);
            }
        }

        for(int j = hSize - 1; j >= 0; j--) {
            for(int i = 0; i < wSize; i++) {
                TileSquare t = tiles[i][j];
                if(t.character != null) t.character.render(sb);
                else if(t.enemy != null) t.enemy.render(sb);
            }
        }

        for(int j = hSize - 1; j >= 0; j--) {
            for(int i = 0; i < wSize; i++) {
                TileSquare t = tiles[i][j];
                if(t.character != null) t.character.hb.render(sb);
                else if(t.enemy != null) t.enemy.hb.render(sb);
            }
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
                int x = pv.x, y = pv.y;
                if (x >= 0 && x < wSize && y >= 0 && y < hSize) {
                    TileSquare tile = tiles[x][y];
                    if(phase == BattlePhase.DIRECTION) {
                        tile.isTarget = true;
                    } else {
                        tile.isTarget = tile.status == TileSquare.TileStatus.NORMAL;
                    }
                    targetTiles.add(tile);
                }
            }
        }
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
