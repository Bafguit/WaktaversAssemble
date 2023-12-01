package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractChar;

public class SynergyFlashAction extends AbstractAction {

    private final AbstractChar.Synergy synergy;

    public SynergyFlashAction(AbstractChar.Synergy s) {
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
