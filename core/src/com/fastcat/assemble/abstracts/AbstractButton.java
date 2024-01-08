package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.handlers.InputHandler;

public abstract class AbstractButton extends ImageTextButton {

    public AbstractButton(TextureAtlas atlas, String id) {
        super("", new Skin(atlas), id);
        setScale(InputHandler.scaleA);
    }

    @Override
    public final void draw(Batch batch, float parentAlpha) {

    }

    public void draw() {
        super.draw(WakTower.application.sb, 1f);
    }
    
}
