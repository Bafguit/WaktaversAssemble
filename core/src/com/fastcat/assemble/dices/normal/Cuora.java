package com.fastcat.assemble.dices.normal;

import com.fastcat.assemble.abstrcts.AbstractDice;
import com.fastcat.assemble.skills.*;

public class Cuora extends AbstractDice {
    public Cuora() {
        super("Cuora", DiceRarity.NORMAL);
    }

    @Override
    protected void defineSkill() {
        skills[0] = new Protector();
        skills[1] = new Protector();
        skills[2] = new DefStrengthenB();
        skills[3] = new ShellDefense();
        skills[4] = new Defender();
        skills[5] = new Defender();
    }
}
