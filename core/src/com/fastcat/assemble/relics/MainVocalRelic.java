package com.fastcat.assemble.relics;

import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.synergies.MainVocal;

public class MainVocalRelic extends AbstractRelic {

    public MainVocalRelic() {
        super("MainVocalRelic");
    }
    
    @Override
    public void onGain() {
        MainVocal.getInstance().baseMemCount++;
    }

    @Override
    public void onLose() {
        MainVocal.getInstance().baseMemCount--;
    }
    
}
