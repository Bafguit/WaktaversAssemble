package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;

public class BattleStartEffectAction extends AbstractAction {

    public BattleStartEffectAction() {
        super(0f);
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            //todo 이펙트 실행
        }
    }
}
