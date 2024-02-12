package com.fastcat.assemble.members;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.member.MemberChoulokyAction;
import com.fastcat.assemble.interfaces.OnIncreaseMemberDamage;

public class Chouloky extends AbstractMember implements OnIncreaseMemberDamage {

    public Chouloky() {
        super("Chouloky");
        setValue(25, 5);
        passive = true;
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    public void onExit() {
        WakTower.game.battle.turnMemberDamage.remove(this);
    }

    @Override
    protected void useMember() {
        next(new MemberChoulokyAction(this));
    }

    @Override
    public int increaseMemberDamage() {
        return 0;
    }

    @Override
    public float multiplyMemberDamage() {
        return 1 + ((float) tempClone.calculateValue() / 100f);
    }
}
