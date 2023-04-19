package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class RollDiceButton extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.ROLL;
    private final BattleScreen screen;

    public RollDiceButton(BattleScreen screen) {
        super(FileHandler.dice.get("Dice"));
        this.screen = screen;
        overable = screen.phase == BattleScreen.BattlePhase.DRAW;
    }

    @Override
    protected void updateButton() {
        overable = screen.phase == BattleScreen.BattlePhase.DRAW;
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if(!overable) sb.setColor(Color.DARK_GRAY);
            else if (!over) sb.setColor(Color.LIGHT_GRAY);
            sb.draw(img, x, y, width, height);
            FontHandler.renderCenter(sb, font, "주사위 굴리기", x + width * 0.5f, y + height * 0.5f);
        }
    }

    @Override
    public void onClick() {
        screen.rollDice();
    }
}
