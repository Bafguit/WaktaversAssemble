package com.fastcat.assemble.screens.temp;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstrcts.AbstractDice;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.dices.basic.NormalDice;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class DiceButton extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.TURN_CHANGE;

    public AbstractDice dice;

    public DiceButton() {
        super(FileHandler.dice.get("Dice"));
        pix();
        dice = new NormalDice();
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled && dice != null) {
            if (overable && !over) sb.setColor(Color.LIGHT_GRAY);
            if (showImg) sb.draw(img, x, y, width, height);
            if (dice.getNumber() > 0) {
                FontHandler.renderCenter(sb, font, Integer.toString(dice.getNumber()), originX, originY);
            }
        }
    }

    @Override
    public void onClick() {
        dice.roll();
    }
}
