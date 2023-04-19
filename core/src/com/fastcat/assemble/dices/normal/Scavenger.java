package com.fastcat.assemble.dices.normal;

import com.fastcat.assemble.abstracts.AbstractDice;
import com.fastcat.assemble.skills.*;

public class Scavenger extends AbstractDice {
    public Scavenger() {
        super("Scavenger", DiceRarity.NORMAL);
    }

    @Override
    protected void defineSkill() {
        skills[0] = new Pioneer();
        skills[1] = new Pioneer();
        skills[2] = new AssaultOrderB();
        skills[3] = new AssaultOrderAttack();
        skills[4] = new Vanguard();
        skills[5] = new Vanguard();
    }
}
