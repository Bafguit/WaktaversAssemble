package com.fastcat.assemble.screens.mainmenu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractGame;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.screens.battle.BattleScreen;

public class StartGameButton extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.MAIN;
    private final FontHandler.FontData fontGrey = FontHandler.MAIN_GREY;

    public StartGameButton() {
        super(FileHandler.getTexture("ui/tile"), 960, 600, 200, 80);
        setData("gameStartButton");
        overable = true;
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
        WakTower.game = new AbstractGame();
        WakTower.battleScreen = new BattleScreen();
        WakTower.setScreen(WakTower.battleScreen);
    }
}
