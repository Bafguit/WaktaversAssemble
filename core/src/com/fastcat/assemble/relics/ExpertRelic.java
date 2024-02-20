package com.fastcat.assemble.relics;

import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.synergies.Expert;

public class ExpertRelic extends AbstractRelic {

    public ExpertRelic() {
        super("ExpertRelic");
    }
    
    @Override
    public void onGain() {
        Expert.getInstance().baseMemCount++;
    }

    @Override
    public void onLose() {
        Expert.getInstance().baseMemCount--;
    }
    
}
