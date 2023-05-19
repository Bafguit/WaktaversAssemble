package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.screens.battle.BattleScreen.BattlePhase;

public class PhaseButton extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.ROLL;
    private final BattleScreen screen;

    public PhaseButton(BattleScreen screen) {
        super(FileHandler.dice.get("Dice"));
        this.screen = screen;
        is3D = false;
    }

    @Override
    protected void updateButton() {
        clickable = false;
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
        if(screen.phase == BattlePhase.READY) {
            screen.phase = BattlePhase.DEPLOY;
        } else if(screen.phase == BattlePhase.DEPLOY) {
            screen.phase = BattlePhase.DRAW;
        } else if(screen.phase == BattlePhase.DRAW) {
            screen.phase = BattlePhase.SKILL;
        } else if(screen.phase == BattlePhase.SKILL) {
            screen.phase = BattlePhase.READY;
            screen.resetDice();
            screen.resetChar();
        } else if(screen.phase == BattlePhase.END_TURN) {
            screen.rollDice();
        }
    }
}
