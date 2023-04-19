package com.fastcat.assemble.skills;

import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractSkill;
import com.fastcat.assemble.actions.IncreasedHealAction;

public class Medic extends AbstractSkill {

    private static final String ID = "Medic";

    public Medic() {
        super(ID, SkillTarget.SELF, 1);
    }

    @Override
    protected void useSkill() {
        top(new IncreasedHealAction(targets, MousseAdventure.game.player, 100, false));
    }

    @Override
    protected void defineRange() {}
}
