package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractEntity;

public class PlayAnimationAction extends AbstractAction {

    public AbstractEntity entity;
    public String key;

    public PlayAnimationAction(AbstractEntity m) {
        this(m, "skill", 0);
    }

    public PlayAnimationAction(AbstractEntity m, float delay) {
        this(m, "skill", delay);
    }

    public PlayAnimationAction(AbstractEntity m, String key) {
        this(m, key, 0);
    }

    public PlayAnimationAction(AbstractEntity m, String key, float delay) {
        super(delay);
        this.key = key;
        entity = m;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            entity.animation.setAnimation(key);
            entity.animation.addAnimation("idle");
        }
    }
    
}
