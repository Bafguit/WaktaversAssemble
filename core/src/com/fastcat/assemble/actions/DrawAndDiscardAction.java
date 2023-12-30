package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.interfaces.OnTempScreenClosed;

public class DrawAndDiscardAction extends AbstractAction implements OnTempScreenClosed {

    public int discard;

    public DrawAndDiscardAction(int amount) {
        this(amount, amount);
    }

    public DrawAndDiscardAction(int draw, int discard) {
        super(1.5f);
        this.amount = draw;
        this.discard = discard;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            WakTower.game.battle.draw(amount);
        } else if(duration <= 1 && run) {
            run = false;
            //todo DiscardScreen (Temp)
        }
    }

    @Override
    public void onTempScreenClosed() {
        run = true;
        isDone = true;
    }
}
