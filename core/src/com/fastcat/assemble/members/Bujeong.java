package com.fastcat.assemble.members;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.PlayAnimationAction;

public class Bujeong extends AbstractMember {

    public Bujeong() {
        super("Bujeong");
        setValue(15, 5);
    }

    @Override
    public boolean isEvaded() {
        return WakTower.game.battleRandom.randomBoolean((float) calculateValue() / 100f);
    }

    @Override
    protected void onSummoned() {
        use();
    }

    @Override
    protected void useMember() {
        next(new PlayAnimationAction(this, 0.3f));
    }
}
