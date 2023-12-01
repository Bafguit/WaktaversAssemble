package com.fastcat.assemble.screens.mainmenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractGame;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.battles.TestBattle;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.screens.battle.BattleScreen;

import static com.fastcat.assemble.WakTower.battleScreen;

public class StartButton extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.TURN_CHANGE;

    public StartButton() {
        super(FileHandler.dice.get("Dice"));
        setPosition(960, 400);
        is3D = false;
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if (overable && !over) sb.setColor(Color.LIGHT_GRAY);
            //sb.draw(img, x, y, width, height);
            FontHandler.renderCenter(sb, font, "게임 시작", x + width * 0.5f, y + height * 0.5f);
        }
    }

    @Override
    public void onClick() {
        WakTower.game = new AbstractGame();
        battleScreen = new BattleScreen(new TestBattle());
        WakTower.application.screen = battleScreen;
    }
}
