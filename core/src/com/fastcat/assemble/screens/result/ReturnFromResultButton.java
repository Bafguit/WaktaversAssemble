package com.fastcat.assemble.screens.result;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

import static com.fastcat.assemble.MousseAdventure.battleScreen;
import static com.fastcat.assemble.MousseAdventure.mainMenuScreen;

public class ReturnFromResultButton extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.TURN_CHANGE;

    public ReturnFromResultButton() {
        super(FileHandler.dice.get("Dice"));
        setPosition(960, 400);
        is3D = false;
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if (overable && !over) sb.setColor(Color.LIGHT_GRAY);
            //sb.draw(img, x, y, width, height);
            FontHandler.renderCenter(sb, font, "돌아가기", x + width * 0.5f, y + height * 0.5f);
        }
    }

    @Override
    public void onClick() {
        MousseAdventure.application.screen = mainMenuScreen;
    }
}
