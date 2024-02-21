package com.fastcat.assemble.relics;

import com.fastcat.assemble.abstracts.AbstractRelic;

public class CommonRelic2 extends AbstractRelic {

    public CommonRelic2() {
        super("CommonRelic2");
    }

    @Override
    public int calculateAtk(int atk) {
        return atk + 1;
    }
}
