package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.abstrcts.AbstractSkill.SkillDir;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;

import static com.fastcat.assemble.screens.battle.BattleScreen.BattlePhase.*;

public class DirectionButton extends AbstractUI {

    public BattleScreen screen;
    public final SkillDir dir;

    private final int angle;

    public DirectionButton(BattleScreen screen, SkillDir dir, int x, int y) {
        super(FileHandler.ui.get("DIR"));
        this.screen = screen;
        clickable = false;
        overable = false;
        this.dir = dir;
        pos = new Vector2(x, y);
        if(dir == SkillDir.RIGHT) {
            angle = -90;
        } else if(dir == SkillDir.DOWN) {
            angle = 180;
        } else if(dir == SkillDir.LEFT) {
            angle = 90;
        } else angle = 0;
    }

    @Override
    protected void updateButton() {
        if(dir == SkillDir.UP) {
            setPosition(screen.player.originX, screen.player.originY + 100);
        } else if(dir == SkillDir.RIGHT) {
            setPosition(screen.player.originX + 100, screen.player.originY);
        } else if(dir == SkillDir.DOWN) {
            setPosition(screen.player.originX, screen.player.originY - 100);
        } else if(dir == SkillDir.LEFT) {
            setPosition(screen.player.originX - 100, screen.player.originY);
        }
    }

    @Override
    public void onClick() {
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if(screen.curDir != dir) sb.setColor(Color.LIGHT_GRAY);
            if(screen.phase == DIRECTION) sb.draw(img, x, y, 0 + width * 0.5f, 0 + height * 0.5f, width, height, 1, 1, angle);
        }
    }
}
