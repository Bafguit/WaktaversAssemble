package com.fastcat.assemble.relics;

import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.synergies.Competitor;

public class CompetitorRelic extends AbstractRelic {

    public CompetitorRelic() {
        super("CompetitorRelic");
    }
    
    @Override
    public void onGain() {
        Competitor.getInstance().baseMemCount++;
    }

    @Override
    public void onLose() {
        Competitor.getInstance().baseMemCount--;
    }
    
}
