package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.actions.GainGoldAction;
import com.fastcat.assemble.handlers.ActionHandler;

public class Villain extends AbstractSynergy {

    private static final int max_gold = 20;
    private static Villain instance;

    private Villain() {
        super("Villain");
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        super.endOfTurn(isPlayer);
        int gold = memberCount * grade;
        if(counter < max_gold) {
            flash();
            int comb = counter + gold;
            if(comb > max_gold)
                gold -= comb - max_gold;
            counter += gold;
            ActionHandler.next(new GainGoldAction(gold));
        }
    }

    @Override
    public void endOfBattle() {
        super.endOfBattle();
        counter = 0;
    }

    public static Villain getInstance() {
        if(instance == null) {
            instance = new Villain();
        }
        return instance;
    }
}