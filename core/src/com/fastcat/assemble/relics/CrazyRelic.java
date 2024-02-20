package com.fastcat.assemble.relics;

import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.synergies.Crazy;

public class CrazyRelic extends AbstractRelic {

    public CrazyRelic() {
        super("CrazyRelic");
    }
    
    @Override
    public void onGain() {
        Crazy.getInstance().baseMemCount++;
    }

    @Override
    public void onLose() {
        Crazy.getInstance().baseMemCount--;
    }
    
}
