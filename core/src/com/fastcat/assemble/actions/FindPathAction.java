package com.fastcat.assemble.actions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.screens.battle.EnemyButton;
import com.fastcat.assemble.utils.BulkPathFinder;

import static com.fastcat.assemble.MousseAdventure.battleScreen;

public class FindPathAction extends AbstractAction {

    public FindPathAction() {
        super(2);
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            BulkPathFinder.clear();
            BulkPathFinder.setFinishCallback(this::finishFinding);
            for(EnemyButton e : battleScreen.enemies) {
                if(e.entity.isAlive()) {
                    e.target = e.entity.getPlayerInRange(e, e.entity.getRange());
                }
            }
            for(EnemyButton e : battleScreen.enemies) {
                if(e.entity.isAlive()) {
                    BulkPathFinder.performPathfinding(e.pos, battleScreen.player.pos, e::setPath);
                }
            }
        }

        if(!isDone) {
            duration = 1;
        }
    }

    private Void finishFinding() {
        isDone = true;
        return null;
    }

    @Override
    protected void renderAction(SpriteBatch sb) {

    }
}