package com.fastcat.assemble.character;

import com.esotericsoftware.spine.AnimationState;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractPlayer;

public class Mousse extends AbstractPlayer {
    public Mousse() {
        super("char_185_frncat", 705, 2345, 392, 15);
    }

    @Override
    public void attackAnimation(int target, AnimationState.AnimationStateAdapter adapter) {
        animation.setAndIdle(target > 1 ? "Attack_2" : "Attack", adapter);
    }
}
