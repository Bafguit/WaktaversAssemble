package com.fastcat.assemble.relics;

import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.synergies.Villain;

public class VillainRelic extends AbstractRelic {

    public VillainRelic() {
        super("VillainRelic");
    }
    
    @Override
    public void onGain() {
        Villain.getInstance().baseMemCount++;
    }

    @Override
    public void onLose() {
        Villain.getInstance().baseMemCount--;
    }
    
}
