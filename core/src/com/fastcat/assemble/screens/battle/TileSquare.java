package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;

import static com.fastcat.assemble.screens.battle.BattleScreen.BattlePhase.*;

public class TileSquare extends AbstractUI {

    public BattleScreen screen;
    public CharacterButton character;
    public DiceButton dice;
    public final TileStatus staticStatus;
    public TileStatus status;
    public EnemyButton enemy;

    public TileSquare(BattleScreen screen, TileStatus status, int x, int y) {
        super(FileHandler.ui.get("TILE"));
        this.screen = screen;
        this.status = staticStatus = status;
        clickable = false;
        pos = new Vector2(x, y);
    }

    @Override
    protected void updateButton() {
        clickable = screen.phase == DEPLOY && enemy == null && status == TileStatus.NORMAL;
        if(screen.tracking != null && over) {
            screen.overTile = this;
        }
    }

    @Override
    public void onClick() {
        screen.player.tile = this;
        character = screen.player;
        character.pos = new Vector2(pos);
        character.character.pos = new Vector2(x, y);
        screen.phase = DRAW;
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if(status == TileStatus.TARGET) sb.setColor(Color.YELLOW);
            else if(!over || !clickable) {
                sb.setColor(Color.LIGHT_GRAY);
            }
            if (showImg) sb.draw(img, x, y, width, height);
        }
    }

    public enum TileStatus {
        NORMAL, BLOCKED, LOCKED, TARGET, INVALID
    }
}
