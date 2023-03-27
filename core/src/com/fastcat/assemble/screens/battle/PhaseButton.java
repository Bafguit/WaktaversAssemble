package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class PhaseButton extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.ROLL;
    private final BattleScreen screen;

    public PhaseButton(BattleScreen screen) {
        super(FileHandler.dice.get("Dice"));
        this.screen = screen;
    }

    @Override
    protected void updateButton() {
        overable = screen.phase != BattleScreen.BattlePhase.DIRECTION && screen.phase != BattleScreen.BattlePhase.DEPLOY;
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if(!overable) sb.setColor(Color.DARK_GRAY);
            else if (!over) sb.setColor(Color.LIGHT_GRAY);
            sb.draw(img, x, y, width, height);
            FontHandler.renderCenter(sb, font, screen.phase.name(), x + width * 0.5f, y + height * 0.5f);
        }
    }

    @Override
    public void onClick() {
        if(screen.phase == BattleScreen.BattlePhase.READY) {
            screen.phase = BattleScreen.BattlePhase.DEPLOY;
        } else if(screen.phase == BattleScreen.BattlePhase.DEPLOY) {
            screen.phase = BattleScreen.BattlePhase.DRAW;
        } else if(screen.phase == BattleScreen.BattlePhase.DRAW) {
            screen.phase = BattleScreen.BattlePhase.SKILL;
        } else if(screen.phase == BattleScreen.BattlePhase.SKILL) {
            screen.phase = BattleScreen.BattlePhase.READY;
            screen.resetDice();
            screen.resetChar();
        }
    }
}
