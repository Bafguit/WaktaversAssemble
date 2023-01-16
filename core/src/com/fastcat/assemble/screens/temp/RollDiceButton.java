package com.fastcat.assemble.screens.temp;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class RollDiceButton extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.TURN_CHANGE;
    private final BattleScreen screen;

    public RollDiceButton(BattleScreen screen) {
        super(FileHandler.dice.get("Dice"));
        this.screen = screen;
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if (overable && !over) sb.setColor(Color.LIGHT_GRAY);
            sb.draw(img, x, y, width, height);
            FontHandler.renderCenter(sb, font, "주사위 굴리기", originX, originY);
        }
    }

    @Override
    public void onClick() {
        screen.rollDice();
    }
}
