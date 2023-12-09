package com.fastcat.assemble.screens.mainmenu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractScreen;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.InputHandler;

public class MainMenuScreen extends AbstractScreen {

    private final StartGameButton start;
    private final LoadGameButton loadGameButton;
    private final DictionaryButton dictionaryButton;
    private final SettingButton settingButton;

    public MainMenuScreen() {
        super(ScreenType.BASE);
        start = new StartGameButton();
        loadGameButton = new LoadGameButton();
        dictionaryButton = new DictionaryButton();
        settingButton = new SettingButton();
    }

    @Override
    public void update() {
        start.update();
        loadGameButton.update();
        dictionaryButton.update();
        settingButton.update();
    }

    @Override
    protected void render(SpriteBatch sb) {
        start.render(sb);
        loadGameButton.render(sb);
        dictionaryButton.render(sb);
        settingButton.render(sb);
        FontHandler.renderCenter(WakTower.application.sb, FontHandler.LOGO, "WAKTOWER", 0,
                800 * InputHandler.scaleY, 1920 * InputHandler.scaleX);
    }
}
