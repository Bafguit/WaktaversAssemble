package com.fastcat.assemble.abstracts;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Villain extends AbstractSynergy {

    private static final int max_gold = 20;
    private static Villain instance;

    private Villain() {
        super("Villain");
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        flash();
        int gold = memberCount * grade;
        if(counter < max_gold) {
            int comb = counter + gold;
            if(comb > max_gold)
                gold -= comb - max_gold;
            ActionHandler.next(new GainGoldAction(gold));
        }
    }

    public static Villain getInstance() {
        if(instance == null) {
            instance = new Villain();
        }
        return instance;
    }
}