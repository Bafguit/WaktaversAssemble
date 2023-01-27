package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstrcts.AbstractAction;
import com.fastcat.assemble.effects.MoveSmallCardEffect;
import com.fastcat.assemble.screens.battle.CardButton;

public class MoveCardToDiscardPileAction extends AbstractAction {

    private final CardButton button;

    public MoveCardToDiscardPileAction(CardButton button) {
        super(0);
        this.button = button;
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            button.screen.effectHandler.addEffect(new MoveSmallCardEffect(button, button.localX, button.localY, 1900, 20));
        }
    }
}
