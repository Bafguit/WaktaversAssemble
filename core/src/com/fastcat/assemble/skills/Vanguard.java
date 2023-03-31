package com.fastcat.assemble.skills;

import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.actions.MovePlayerAction;
import com.fastcat.assemble.utils.Vector2i;

public class Vanguard extends AbstractSkill {

    private static final String ID = "Vanguard";

    public Vanguard() {
        super(ID, SkillTarget.MOVE, 1);
    }

    @Override
    protected void useSkill() {
        top(new MovePlayerAction(MousseAdventure.battleScreen.player, toTile, false));
    }

    @Override
    protected void defineRange() {
        range[0] = new Vector2i(0, 1);
    }
}
