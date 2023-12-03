package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractEntity;

public class GainBlockAction extends AbstractAction {

    public GainBlockAction(AbstractEntity target, int blockAmount) {
        this(target, blockAmount, false);
    }

    public GainBlockAction(AbstractEntity target, int blockAmount, boolean isFast) {
        super(target, isFast ? 0.3f : 1.0f);
        amount = blockAmount;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            //todo 이펙트
            for(AbstractEntity e : target) {
                e.gainBlock(amount);
            }
        }
    }
}
