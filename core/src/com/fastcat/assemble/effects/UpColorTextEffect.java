package com.fastcat.assemble.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.MouseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEffect;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.InputHandler;

import static com.fastcat.assemble.handlers.FontHandler.renderCenter;

public class UpColorTextEffect extends AbstractEffect {

    private final FontHandler.FontData font;
    private final String text;

    public UpColorTextEffect(float x, float y, int damage, Color color) {
        super(x, y, 0.4f);
        font = new FontHandler.FontData(FontHandler.SUB_DESC.size, color, false, false);
        damage = Math.max(damage, 0);
        text = String.valueOf(damage);
    }

    @Override
    protected void renderEffect(SpriteBatch sb) {
        if (duration != baseDuration) {
            if (duration <= baseDuration / 2) {
                font.alpha -= MouseAdventure.tick / baseDuration * 2;
                if (font.alpha < 0) font.alpha = 0;
            }
            y += MouseAdventure.tick * 100 * InputHandler.scaleY;
        }
        renderCenter(sb, font, text, x, y + 80 * InputHandler.scaleY, 100 * InputHandler.scaleX);
    }

    @Override
    public void dispose() {
        font.dispose();
    }


}
