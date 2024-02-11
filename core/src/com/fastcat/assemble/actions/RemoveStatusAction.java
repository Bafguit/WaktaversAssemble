package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractStatus;

public class RemoveStatusAction extends AbstractAction {

    private final AbstractStatus status;

    public RemoveStatusAction(AbstractEntity target, AbstractStatus status) {
        super(target, 0);
        this.status = status;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(target.size > 0) {
                for(AbstractEntity e : target) {
                    e.status.remove(status);
                    status.onRemove();
                }
            } else isDone = true;
        }
    }
}
