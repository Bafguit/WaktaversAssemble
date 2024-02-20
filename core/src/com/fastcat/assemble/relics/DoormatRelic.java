package com.fastcat.assemble.relics;

import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.synergies.Doormat;

public class DoormatRelic extends AbstractRelic {

    public DoormatRelic() {
        super("DoormatRelic");
    }
    
    @Override
    public void onGain() {
        Doormat.getInstance().baseMemCount++;
    }

    @Override
    public void onLose() {
        Doormat.getInstance().baseMemCount--;
    }
    
}
