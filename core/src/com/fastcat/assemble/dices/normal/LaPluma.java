package com.fastcat.assemble.dices.normal;

import com.fastcat.assemble.abstracts.AbstractDice;
import com.fastcat.assemble.skills.*;

public class LaPluma extends AbstractDice {
    public LaPluma() {
        super("LaPluma", DiceRarity.NORMAL);
    }

    @Override
    protected void defineSkill() {
        skills[0] = new Reaper();
        skills[1] = new Reaper();
        skills[2] = new RapidSlashing();
        skills[3] = new Reap();
        skills[4] = new Guard();
        skills[5] = new Guard();
    }
}
