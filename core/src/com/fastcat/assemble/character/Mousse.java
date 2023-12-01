package com.fastcat.assemble.character;

import com.esotericsoftware.spine.AnimationState;

public class Mousse extends AbstractPlayer {
    public Mousse() {
        super("char_185_frncat", 70500, 2345, 392, 15);
    }

    @Override
    public void attackAnimation(int target, AnimationState.AnimationStateAdapter adapter) {
        animation.setAndIdle(target > 1 ? "Attack_2" : "Attack", adapter);
    }
}
