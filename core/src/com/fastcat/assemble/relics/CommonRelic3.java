package com.fastcat.assemble.relics;

import com.fastcat.assemble.abstracts.AbstractRelic;

public class CommonRelic3 extends AbstractRelic {

    public CommonRelic3() {
        super("CommonRelic3");
    }

    @Override
    public int calculateDef(int atk) {
        return atk + 1;
    }
}
