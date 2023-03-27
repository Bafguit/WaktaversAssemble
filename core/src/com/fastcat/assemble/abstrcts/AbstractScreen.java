package com.fastcat.assemble.abstrcts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MouseAdventure;
import com.fastcat.assemble.handlers.EffectHandler;

public abstract class AbstractScreen implements Screen {

    public final EffectHandler effectHandler = new EffectHandler();

    public final Array<AbstractUI> ui = new Array<>();
    public final ScreenType type;
    public Sprite background;

    public AbstractScreen(ScreenType type) {
        this.type = type;
    }

    public abstract void update();

    protected abstract void render(SpriteBatch sb);

    public void render(float delta) {
        MouseAdventure.application.sb.setColor(Color.WHITE);
        if(background != null) {
            MouseAdventure.application.sb.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        render(MouseAdventure.application.sb);
        effectHandler.render(MouseAdventure.application.sb);
    }

    public void setBg(Sprite s) {
        background = s;
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

    public final EffectHandler getEffectHandler() {
        return effectHandler;
    }

    public enum ScreenType {
        BASE, TEMP
    }
}
