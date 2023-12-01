package com.fastcat.assemble.screens.mainmenu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractScreen;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.InputHandler;

public class MainMenuScreen extends AbstractScreen {

    private final StartButton start;

    public MainMenuScreen() {
        super(ScreenType.BASE);
        start = new StartButton();
    }

    @Override
    public void update() {
        start.update();
    }

    @Override
    protected void render(SpriteBatch sb) {
        start.render(sb);
        FontHandler.renderCenter(WakTower.application.sb, FontHandler.LOGO, "MOUSSE'S ADVENTURE", 0,
                800 * InputHandler.scaleY, 1920 * InputHandler.scaleX);
    }
}
