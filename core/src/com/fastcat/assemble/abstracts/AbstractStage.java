package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public abstract class AbstractStage extends Stage {

    private final Table background = new Table();

    public AbstractStage() {
        background.setSize(1920, 1080);
        this.addActor(background);
    }

    public final void focusInput() {
        Gdx.input.setInputProcessor(this);
    }

    public void setBackground(Drawable image) {
        background.setBackground(image);
    }
}
