package com.fastcat.assemble.actions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstracts.AbstractAction;

public class ApplyStatusAction extends AbstractAction {

    private final AbstractStatus status;

    public ApplyStatusAction(Array<AbstractEntity> target, AbstractStatus status, boolean isFast) {
        super(isFast ? 0.1f : 0.5f);
        this.target = target;
        this.status = status;
    }

    public ApplyStatusAction(AbstractEntity target, AbstractStatus status, boolean isFast) {
        super(isFast ? 0.1f : 0.5f);
        this.target.add(target);
        this.status = status;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(target.size > 0) {
                for(AbstractEntity e : target) {
                    if(e.isAlive()) {
                        e.applyStatus(status);
                    }
                }
            } else isDone = true;
        }
    }

    @Override
    protected void renderAction(SpriteBatch sb) {

    }
}
