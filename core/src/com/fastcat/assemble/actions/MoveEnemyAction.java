package com.fastcat.assemble.actions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractAction;
import com.fastcat.assemble.effects.MoveEnemyEffect;
import com.fastcat.assemble.handlers.EffectHandler;
import com.fastcat.assemble.screens.battle.TileSquare;

public class MoveEnemyAction extends AbstractAction {

    private final Array<MoveEnemyEffect> e = new Array<>();

    public MoveEnemyAction(boolean isFast) {
        super(isFast ? 0.15f : 0.3f);
        for(TileSquare[] t1 : MousseAdventure.battleScreen.tiles) {
            for(TileSquare t2 : t1) {
                if(t2.enemy != null) {
                    e.add(new MoveEnemyEffect(t2.enemy, baseDuration));
                }
            }
        }
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(e.size > 0) {
                for(MoveEnemyEffect m : e) {
                    m.setRightNextPath();
                }
                for(MoveEnemyEffect m : e) {
                    EffectHandler.add(m);
                }
            } else isDone = true;
        }

        if(isDone) {
            for(MoveEnemyEffect m : e) {
                m.isDone = true;
            }
        }
    }

    @Override
    protected void renderAction(SpriteBatch sb) {

    }
}
