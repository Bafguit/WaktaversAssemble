package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.members.Hikiking;
import com.fastcat.assemble.members.Victory;

public class DrawButton extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.ROLL.cpy();

    public DrawButton() {
        super(FileHandler.getTexture("ui/tile"));
        setPosition(1420, 400);
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if(!over) sb.setColor(Color.LIGHT_GRAY);
            sb.draw(img, x, y, width, height);
            FontHandler.renderCenter(sb, font, "드로우", x, y);
        }
    }

    @Override
    public void onClick() {
        WakTower.battleScreen.addHand(WakTower.game.battleRandom.random(0, 1) == 1 ? new Victory() : new Hikiking());
    }
}
