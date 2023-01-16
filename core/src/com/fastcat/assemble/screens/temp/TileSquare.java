package com.fastcat.assemble.screens.temp;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;

public class TileSquare extends AbstractUI {

    public DiceRollScreen screen;
    public DiceButton dice;

    public TileSquare(DiceRollScreen screen) {
        super(FileHandler.ui.get("TILE"));
        this.screen = screen;
        clickable = false;
    }

    @Override
    protected void updateButton() {
        if(screen.tracking != null && over) {
            screen.overTile = this;
        }
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if (overable) {
                if(over && dice != null) sb.setColor(Color.LIGHT_GRAY);
                else if(!over) sb.setColor(Color.DARK_GRAY);
            }
            if (showImg) sb.draw(img, x, y, width, height);
        }
    }
}
