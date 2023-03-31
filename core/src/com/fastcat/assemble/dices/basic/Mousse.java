package com.fastcat.assemble.dices.basic;

import com.fastcat.assemble.abstrcts.AbstractDice;
import com.fastcat.assemble.skills.*;

public class Mousse extends AbstractDice {
    public Mousse() {
        super("Mousse", DiceRarity.BASIC);
    }

    @Override
    protected void defineSkill() {
        skills[0] = new ArtsFighter();
        skills[1] = new ArtsFighter();
        skills[2] = new CatScratch();
        skills[3] = new BristlingFur();
        skills[4] = new Vanguard();
        skills[5] = new Vanguard();
    }
}
