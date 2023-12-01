package com.fastcat.assemble.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractEffect;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.InputHandler;

import static com.fastcat.assemble.handlers.FontHandler.renderCenter;

public class UpColorTextEffect extends AbstractEffect {

    private final FontHandler.FontData font;
    private final String text;

    public UpColorTextEffect(float x, float y, int damage, Color color) {
        super(x, y, 0.4f);
        font = new FontHandler.FontData(FontHandler.NB26.size, color, false, false);
        damage = Math.max(damage, 0);
        text = String.valueOf(damage);
        ui.basis = AbstractUI.BasisType.CENTER_LEFT;
        ui.setX(ui.originX + (MathUtils.random(0, 20) - 10) * InputHandler.scaleX);
        ui.setY(ui.originY + MathUtils.random(0, 10) * InputHandler.scaleY);
    }

    @Override
    protected void renderEffect(SpriteBatch sb) {
        if (duration != baseDuration) {
            if (duration <= baseDuration / 2) {
                font.alpha -= WakTower.tick / baseDuration * 2;
                if (font.alpha < 0) font.alpha = 0;
            }
            ui.originY += WakTower.tick * 100;
        }
        ui.update();
        renderCenter(sb, font, text, ui.x, ui.y + 80 * InputHandler.scaleY);
    }

    @Override
    public void dispose() {
        font.dispose();
    }


}
