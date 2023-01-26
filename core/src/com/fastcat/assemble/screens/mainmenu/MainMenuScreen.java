package com.fastcat.assemble.screens.mainmenu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstrcts.AbstractScreen;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.InputHandler;

public class MainMenuScreen extends AbstractScreen {
    public MainMenuScreen() {
        super(ScreenType.BASE);
    }

    @Override
    public void update() {

    }

    @Override
    protected void render(SpriteBatch sb) {
        FontHandler.renderCenter(sb, FontHandler.LOGO, "WAKTAVERSE ASSEMBLE", 0,
                800 * InputHandler.scaleY, 1920 * InputHandler.scaleX);
    }
}
