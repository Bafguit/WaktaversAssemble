package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractSynergy;

public class SynergyFlashAction extends AbstractAction {

    private final AbstractSynergy synergy;

    public SynergyFlashAction(AbstractSynergy s) {
        super(0f);
        this.synergy = s;
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            //todo SynergyFlash
        }
    }
}
