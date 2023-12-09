package com.fastcat.assemble.screens.mainmenu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class LoadGameButton extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.TURN_CHANGE;
    private final FontHandler.FontData fontGrey = FontHandler.TURN_CHANGE;

    public LoadGameButton() {
        super(FileHandler.getTexture("ui/tile"), 960, 500, 200, 80);
        setData("loadGameButton");
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            FontHandler.renderCenter(sb, overable && over ? font : fontGrey, data.text[0], x + width * 0.5f, y + height * 0.5f);
        }
    }

    @Override
    public void onClick() {
        //todo 화면전환 추가
    }
}
