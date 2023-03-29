package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractGame;
import com.fastcat.assemble.abstrcts.AbstractScreen;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.dices.basic.Mousse;
import com.fastcat.assemble.handlers.InputHandler;

import static com.fastcat.assemble.MousseAdventure.cam;

public class BattleScreen extends AbstractScreen {

    public BattlePhase phase;
    public ResizeButton resizeButton;
    public RollDiceButton rollButton;
    public PhaseButton phaseButton;
    public DiceButton[] dice = new DiceButton[6];
    public DirectionButton[] dirButton = new DirectionButton[4];
    public CharacterButton player;
    public AbstractUI tracking;
    public TileSquare overTile;
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
        EnemyButton e = new EnemyButton(this);
        setTile(e, 3, 1);
        TileSquare t = tiles[3][1];
        e.tile = t;
        e.entity.pos = new Vector2(t.originX, t.originY);
        t.enemy = e;
        enemies.add(e);
    }

    @Override
    public void update() {
        resizeButton.update();
        rollButton.update();
        phaseButton.update();

        for (DiceButton b : dice) {
            b.update();
        }

        player.update();

        for(EnemyButton e : enemies) {
            e.update();
        }

        if(phase == BattlePhase.DIRECTION) {
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
    public void render(float delta) {
        MousseAdventure.application.sb.setColor(Color.WHITE);
        if(background != null) {
            MousseAdventure.application.sb.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        render(MousseAdventure.application.sb);
        effectHandler.render(MousseAdventure.application.sb);
    }

    @Override
    public void render(SpriteBatch sb) {
        resizeButton.render(sb);
        rollButton.render(sb);
        phaseButton.render(sb);

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

        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
        cam.position.set(w * 0.5f, h * 0.4f, 600 * InputHandler.scaleA);
        cam.lookAt(w * 0.5f, h * 0.5f,0);
        cam.update();

        for(DiceButton b : dice) {
            b.render(sb);
        }
    }

    public void updateTileStatus() {
        for(TileSquare[] t1 : tiles) {
            for(TileSquare t2 : t1) {
                t2.status = t2.staticStatus;
            }
        }
        if(phase == BattlePhase.DIRECTION) {
            dirSkill.setTempRange(curDir);
            for (int i = dirSkill.tempRange.length - 1; i >= 0; i--) {
                Vector2 pv = new Vector2(player.pos);
                pv.add(dirSkill.tempRange[i]);
                int x = (int) pv.x, y = (int) pv.y;
                if (x >= 0 && x < wSize && y >= 0 && y < hSize) {
                    TileSquare tile = tiles[x][y];
                    tile.status = TileSquare.TileStatus.TARGET;
                    targetTiles.add(tile);
                }
            }
        }
    }

    public void setTile(AbstractUI ui, int x, int y) {
        ui.pos.set(x, y);
        ui.setPosition(610 + 100 * x, 390 + 100 * y);
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
        END_TURN,
        TURN_CHANGE,
        ENEMY,
        END
    }
}
