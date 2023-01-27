package com.fastcat.assemble.actions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WaktaAssemble;
import com.fastcat.assemble.abstrcts.AbstractAction;
import com.fastcat.assemble.effects.MoveSmallCardEffect;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.screens.battle.CardButton;

public class UseCardAction extends AbstractAction {

    private final CardButton card;

    public UseCardAction(CardButton card) {
        super(0);
        this.card = card;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            card.card.use();
            ActionHandler.top(new MoveCardToDiscardPileAction(card));
        }
    }

    @Override
    protected void renderAction(SpriteBatch sb) {
        card.render(sb);
    }
}
