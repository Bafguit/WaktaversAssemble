package com.fastcat.assemble.dices.basic;

import com.fastcat.assemble.abstrcts.AbstractDice;
import com.fastcat.assemble.skills.BaseAttack;
import com.fastcat.assemble.skills.LordAttack;

public class NormalDice extends AbstractDice {
    public NormalDice() {
        super("Dice", DiceRarity.BASIC);
    }

    @Override
    protected void defineSkill() {
        skills[0] = new BaseAttack(atlas.createSprite(Integer.toString(0)));
        skills[1] = new BaseAttack(atlas.createSprite(Integer.toString(0)));
        skills[2] = new BaseAttack(atlas.createSprite(Integer.toString(0)));
        skills[3] = new LordAttack(atlas.createSprite(Integer.toString(0)));
        skills[4] = new LordAttack(atlas.createSprite(Integer.toString(0)));
        skills[5] = new LordAttack(atlas.createSprite(Integer.toString(0)));
        skills[6] = new LordAttack(atlas.createSprite(Integer.toString(0)));
    }
}
