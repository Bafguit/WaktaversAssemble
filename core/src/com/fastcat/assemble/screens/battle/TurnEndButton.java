package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.actions.AllEnemyAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.screens.battle.BattleScreen.BattlePhase;

public class TurnEndButton extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.ROLL;
    private final BattleScreen screen;

    public TurnEndButton(BattleScreen screen) {
        super(FileHandler.dice.get("Dice"));
        this.screen = screen;
    }

    @Override
    protected void updateButton() {
        overable = screen.phase == BattlePhase.SKILL && !ActionHandler.isRunning;
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if(!overable) sb.setColor(Color.DARK_GRAY);
            else if (!over) sb.setColor(Color.LIGHT_GRAY);
            sb.draw(img, x, y, width, height);
            FontHandler.renderCenter(sb, font, "턴 종료", x + width * 0.5f, y + height * 0.5f);
        }
    }

    @Override
    public void onClick() {
        screen.phase = BattlePhase.END_TURN;
        ActionHandler.bot(new AllEnemyAction(false));
    }
}
