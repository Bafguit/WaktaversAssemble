package com.fastcat.assemble.relics;

import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.synergies.Timid;

public class TimidRelic extends AbstractRelic {

    public TimidRelic() {
        super("TimidRelic");
    }
    
    @Override
    public void onGain() {
        Timid.getInstance().baseMemCount++;
    }

    @Override
    public void onLose() {
        Timid.getInstance().baseMemCount--;
    }
    
}
