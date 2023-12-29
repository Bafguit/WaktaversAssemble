package com.fastcat.assemble.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractEffect;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.InputHandler;

import static com.badlogic.gdx.graphics.Color.alpha;
import static com.fastcat.assemble.handlers.FontHandler.renderCenter;

public class TurnChangeEffect extends AbstractEffect {

    private final FontHandler.FontData font;
    private final String text;

    public TurnChangeEffect(boolean isPlayer) {
        super(2.5f);
        data = DataHandler.getInstance().uiData.get("turnChangeEffect");
        font = new FontHandler.FontData(100, Color.WHITE, false, true);
        text = isPlayer ? data.text[0] : data.text[1];
        font.alpha = 0;
    }

    @Override
    protected void renderEffect(SpriteBatch sb) {
        if (duration <= 1) {
            font.alpha -= WakTower.tick;
            if (font.alpha < 0) font.alpha = 0;
        } else if(duration > 1.5f && duration <= baseDuration) {
            font.alpha += WakTower.tick;
            if (font.alpha > 1) font.alpha = 1;
        }
        renderCenter(sb, font, text, 0, Gdx.graphics.getHeight() * 0.5f, Gdx.graphics.getWidth());
    }

    @Override
    public void dispose() {
        font.dispose();
    }


}
