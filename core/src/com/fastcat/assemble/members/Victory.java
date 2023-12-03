package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.MemberVictoryAction;
import com.fastcat.assemble.interfaces.OnIncreaseGlobalDamage;
import com.fastcat.assemble.synergies.Crazy;
import com.fastcat.assemble.synergies.MindMaster;

public class Victory extends AbstractMember implements OnIncreaseGlobalDamage {

    public Victory(String id) {
        super(id);
        setValue(2, 1);
    }

    @Override
    protected void setSynergy() {
        baseSynergy[0] = Crazy.getInstance();
        baseSynergy[1] = MindMaster.getInstance();
    }

    @Override
    public void onSummon() {
        bot(new MemberVictoryAction(this));
    }

    @Override
    public int increaseGlobalDamage() {
        return value;
    }

    @Override
    public float multiplyGlobalDamage() {
        return 1f;
    }
    
}
