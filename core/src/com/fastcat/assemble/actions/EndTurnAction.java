package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;

public class EndTurnAction extends AbstractAction {
    
    private final boolean isPlayer;

    public EndTurnAction(boolean isPlayer) {
        super(0f);
        this.isPlayer = isPlayer;
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            
        }
    }
}
