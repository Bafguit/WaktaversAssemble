package com.fastcat.assemble.relics;

import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.synergies.Magician;

public class MagicianRelic extends AbstractRelic {

    public MagicianRelic() {
        super("MagicianRelic");
    }
    
    @Override
    public void onGain() {
        Magician.getInstance().baseMemCount++;
    }

    @Override
    public void onLose() {
        Magician.getInstance().baseMemCount--;
    }
    
}
