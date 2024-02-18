package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.handlers.EffectHandler;
import com.fastcat.assemble.interfaces.OnTempScreenClosed;

public abstract class AbstractScreen implements Screen {

    public final EffectHandler effectHandler = new EffectHandler();

    public final Array<AbstractUI> ui = new Array<>();
    public final ScreenType type;
    public Sprite background;

    private Array<OnTempScreenClosed> onClosedListener = new Array<>();

    public AbstractScreen(ScreenType type) {
        this.type = type;
    }

    public abstract void update();

    protected abstract void render(SpriteBatch sb);

    public void render(float delta) {
        WakTower.application.sb.setColor(Color.WHITE);
        if(background != null) {
            WakTower.application.sb.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        render(WakTower.application.sb);
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
        if(type == ScreenType.TEMP) {
            for(OnTempScreenClosed otc : onClosedListener) {
                otc.onTempScreenClosed();
            }
        }
    }

    @Override
    public void dispose() {

    }

    public final void addListener(OnTempScreenClosed l) {
        onClosedListener.add(l);
    }

    public final EffectHandler getEffectHandler() {
        return effectHandler;
    }

    public enum ScreenType {
        BASE, TEMP
    }
}
