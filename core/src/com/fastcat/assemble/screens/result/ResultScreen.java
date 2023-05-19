package com.fastcat.assemble.screens.result;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractScreen;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.screens.mainmenu.StartButton;

public class ResultScreen extends AbstractScreen {

    private final ResultType type;
    private final ReturnFromResultButton start;
    private String text;

    public ResultScreen(ResultType type) {
        super(ScreenType.BASE);
        start = new ReturnFromResultButton();
        this.type = type;
        text = type == ResultType.WIN ? "승리" : "패배";
    }

    @Override
    public void update() {
        start.update();
    }

    @Override
    protected void render(SpriteBatch sb) {
        start.render(sb);
        FontHandler.renderCenter(MousseAdventure.application.sb, FontHandler.LOGO, text, 0,
                800 * InputHandler.scaleY, 1920 * InputHandler.scaleX);
    }

    public enum ResultType {
        WIN, LOSE
    }
}
