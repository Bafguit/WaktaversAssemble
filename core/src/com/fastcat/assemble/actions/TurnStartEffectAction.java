package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;

public class TurnStartEffectAction extends AbstractAction {

    private final boolean isPlayer;

    public TurnStartEffectAction(boolean isPlayer) {
        super(0f);
        this.isPlayer = isPlayer;
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            //todo 이펙트 실행
        }
    }
}
