package com.fastcat.assemble.abstrcts;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public abstract class AbstractScreen implements Screen {

    public final Array<AbstractUI> ui = new Array<>();
    public final ScreenType type;
    public Sprite background;

    public AbstractScreen(ScreenType type) {
        this.type = type;
    }

    public abstract void update();

    public abstract void render(SpriteBatch sb);

    @Override
    public final void render(float delta) {
        if(background != null) {

        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public enum ScreenType {
        BASE, TEMP
    }
}
