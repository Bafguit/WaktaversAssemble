package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.abstracts.AbstractSkill;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.actions.FindPathAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.utils.Vector2i;

import static com.fastcat.assemble.screens.battle.BattleScreen.BattlePhase.*;

public class TileSquare extends AbstractUI {

    public BattleScreen screen;
    public CharacterButton character;
    public EnemyButton enemy;
    public TileStatus status;
    public boolean isTarget = false;

    public TileSquare(BattleScreen screen, TileStatus status, int x, int y) {
        super(FileHandler.ui.get("TILE"));
        this.screen = screen;
        this.status = status;
        clickable = false;
        pos = new Vector2i(x, y);
    }

    public TileSquare(TileStatus status, int x, int y) {
        super(FileHandler.ui.get("TILE"));
        this.status = status;
        clickable = false;
        pos = new Vector2i(x, y);
    }

    @Override
    protected void updateButton() {
        clickable = screen.phase == DEPLOY && enemy == null && status == TileStatus.NORMAL;
    }

    @Override
    public void onClick() {
        status = TileStatus.ENTITY;
        screen.player.tile = this;
        character = screen.player;
        character.pos = new Vector2i(pos);
        character.character.updateDir(AbstractSkill.SkillDir.RIGHT);
        character.character.pos = new Vector2(originX, originY);
        screen.rollDice();
        ActionHandler.bot(new FindPathAction());
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if(isTarget) sb.setColor(Color.YELLOW);
            else if(!over || !clickable) {
                sb.setColor(Color.LIGHT_GRAY);
            }
            if (showImg) sb.draw(img, x, y, width, height);
        }
    }

    public void removeEntity() {
        enemy = null;
        character = null;
        status = TileStatus.NORMAL;
    }

    public enum TileStatus {
        NORMAL, BLOCKED, ENTITY, INVALID
    }
}
