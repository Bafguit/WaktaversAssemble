package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Null;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.uis.TopBar;

public abstract class AbstractStage extends Stage {

    private final Table background = new Table();

    public TopBar topBar;

    public AbstractStage() {
        super(WakTower.viewport);
        background.setFillParent(true);
        this.addActor(background);
    }

    public void afterInitial() {
        setDebugAll(WakTower.debug);
    }

    public void afterInitial(boolean useTopBar) {
        if(useTopBar) {
            topBar = new TopBar();
            this.addActor(topBar);
        }
        setDebugAll(WakTower.debug);
    }

    public AbstractStage(Drawable background) {
        this();
        setBackground(background);
    }

    public AbstractStage(Texture background) {
        this(new TextureRegionDrawable(background));
    }

    public final void focusInput() {
        Gdx.input.setInputProcessor(this);
    }

    public void setBackground(Drawable image) {
        background.setBackground(image);
    }

    public void onRemoved() {}
}
