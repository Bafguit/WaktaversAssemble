package com.fastcat.assemble.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractEffect;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class TurnChangeEffect extends AbstractEffect {

    private final FontHandler.FontData font;
    private final String text;

    public TurnChangeEffect(boolean isPlayer) {
        super(2f);
        data = DataHandler.getInstance().uiData.get("turnChangeEffect");
        font = new FontHandler.FontData(100, Color.WHITE, false, true);
        text = isPlayer ? data.text[0] : data.text[1];
        font.alpha = 0;
    }

    @Override
    public void run() {
        
    }


}
