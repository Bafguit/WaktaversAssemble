package com.fastcat.assemble.relics;

import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.synergies.Kiddo;

public class KiddoRelic extends AbstractRelic {

    public KiddoRelic() {
        super("KiddoRelic");
    }
    
    @Override
    public void onGain() {
        Kiddo.getInstance().baseMemCount++;
    }

    @Override
    public void onLose() {
        Kiddo.getInstance().baseMemCount--;
    }
    
}
