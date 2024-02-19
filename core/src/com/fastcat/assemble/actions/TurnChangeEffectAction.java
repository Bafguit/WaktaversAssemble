package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.effects.TurnChangeEffect;
import com.fastcat.assemble.handlers.EffectHandler;

public class TurnChangeEffectAction extends AbstractAction {

    private final boolean isPlayer;

    public TurnChangeEffectAction(boolean isPlayer) {
        super(2f);
        this.isPlayer = isPlayer;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            EffectHandler.run(new TurnChangeEffect(isPlayer));
        }
    }
}
