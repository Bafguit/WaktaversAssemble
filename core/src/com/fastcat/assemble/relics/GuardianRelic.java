package com.fastcat.assemble.relics;

import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.synergies.Guardian;

public class GuardianRelic extends AbstractRelic {

    public GuardianRelic() {
        super("GuardianRelic");
    }
    
    @Override
    public void onGain() {
        Guardian.getInstance().baseMemCount++;
    }

    @Override
    public void onLose() {
        Guardian.getInstance().baseMemCount--;
    }
    
}
