package com.fastcat.assemble.actions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractEffect;
import com.fastcat.assemble.effects.MoveEnemyEffect;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.EffectHandler;
import com.fastcat.assemble.screens.battle.TileSquare;

import java.util.Iterator;

public class AllEnemyAction extends AbstractAction {

    private final Array<MoveEnemyEffect> e = new Array<>();

    public AllEnemyAction(boolean isFast) {
        super(isFast ? 0.15f : 1.333f);
        for(TileSquare[] t1 : MousseAdventure.battleScreen.tiles) {
            for(TileSquare t2 : t1) {
                if(t2.enemy != null) {
                    AbstractEffect ef = t2.enemy.entity.playAction(t2.enemy, baseDuration);
                    if(ef instanceof MoveEnemyEffect) {
                        e.add((MoveEnemyEffect) ef);
                    } else {
                        EffectHandler.add(ef);
                    }
                }
            }
        }
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(e.size > 0) {
                boolean moveDone;
                Array<MoveEnemyEffect> me = new Array<>(e);
                do {
                    moveDone = false;
                    Iterator<MoveEnemyEffect> itr = me.iterator();
                    while(itr.hasNext()) {
                        MoveEnemyEffect m = itr.next();
                        m.setRightNextPath();
                        if(m.moveDone) {
                            itr.remove();
                            moveDone = true;
                        }
                    }
                } while(moveDone);

                for(MoveEnemyEffect m : e) {
                    EffectHandler.add(m);
                }
            } else if(ActionHandler.getEffectHandler().effectList.size == 0) {
                isDone = true;
            }
        }

        if(isDone) {
            for(MoveEnemyEffect m : e) {
                m.isDone = true;
            }
            ActionHandler.bot(new RollDiceAction());
        }
    }

    @Override
    protected void renderAction(SpriteBatch sb) {

    }
}
