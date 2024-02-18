package com.fastcat.assemble.handlers;

import com.fastcat.assemble.abstracts.AbstractEffect;

public final class EffectHandler {

    public static void run(AbstractEffect e) {
        e.run();
    }
}
