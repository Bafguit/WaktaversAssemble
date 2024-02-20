package com.fastcat.assemble.relics;

import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.synergies.Nobles;

public class NoblesRelic extends AbstractRelic {

    public NoblesRelic() {
        super("NoblesRelic");
    }
    
    @Override
    public void onGain() {
        Nobles.getInstance().baseMemCount++;
    }

    @Override
    public void onLose() {
        Nobles.getInstance().baseMemCount--;
    }
    
}
