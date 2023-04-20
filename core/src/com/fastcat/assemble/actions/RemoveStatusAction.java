package com.fastcat.assemble.actions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractStatus;

public class RemoveStatusAction extends AbstractAction {

    private final AbstractStatus status;
    private final boolean isFast;

    public RemoveStatusAction(Array<AbstractEntity> target, AbstractStatus status, boolean isFast) {
        super(0.5f);
        this.isFast = isFast;
        this.target = target;
        this.status = status;
    }

    public RemoveStatusAction(AbstractEntity target, AbstractStatus status, boolean isFast) {
        super(0.5f);
        this.isFast = isFast;
        this.target.add(target);
        this.status = status;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(target.size > 0) {
                for(AbstractEntity e : target) {
                    if(e.isAlive()) {
                        e.removeStatus(status);
                    }
                }
                isDone = isFast;
            } else isDone = true;
        }
    }

    @Override
    protected void renderAction(SpriteBatch sb) {

    }
}
