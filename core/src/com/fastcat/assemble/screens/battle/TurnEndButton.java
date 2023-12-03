package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractBattle.BattlePhase;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.actions.EndTurnAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class TurnEndButton extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.TURN_CHANGE;

    public TurnEndButton() {
        super(FileHandler.getTexture("ui/turnEnd"));
        setPosition(960, 400);
    }
    
    @Override
    protected void foreUpdate() {
        clickable = WakTower.game.battle.phase == BattlePhase.playerTurn; 
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if (overable && !over) sb.setColor(Color.LIGHT_GRAY);
            sb.draw(img, x, y, width, height);
            FontHandler.renderCenter(sb, font, "턴 종료", x + width * 0.5f, y + height * 0.5f);
        }
    }

    @Override
    public void onClick() {
        ActionHandler.bot(new EndTurnAction(true));
    }
}
