package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractBattle.BattlePhase;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.actions.EndTurnAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class TurnEndButton extends AbstractUI {

    private final FontHandler.FontData enabledFont = FontHandler.TURN_CHANGE.cpy();
    private final FontHandler.FontData disabledFont = FontHandler.TURN_CHANGE.cpy();
    private final Sprite disabledImg;

    public TurnEndButton() {
        super(FileHandler.getTexture("ui/turnEnd"));
        setPosition(960, 400);
        disabledImg = new Sprite(FileHandler.getTexture("ui/turnEndDisabled"));
        data = DataHandler.getInstance().uiData.get("turnEndButton");
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if (overable && !over) sb.setColor(Color.LIGHT_GRAY);
            if(clickable) {
                sb.draw(img, x, y, width, height);
                FontHandler.renderCenter(sb, enabledFont, data.text[0], x, y);
            } else {
                sb.draw(disabledImg, x, y, width, height);
                FontHandler.renderCenter(sb, disabledFont, data.text[0], x, y);
            }
        }
    }

    @Override
    protected void onClick() {
        clickable = false;
        ActionHandler.bot(new EndTurnAction(true));
    }
}
