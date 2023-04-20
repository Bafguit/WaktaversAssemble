package com.fastcat.assemble.actions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.screens.battle.CharacterButton;
import com.fastcat.assemble.screens.battle.TileSquare;
import com.fastcat.assemble.utils.Vector2i;

public class MovePlayerAction extends AbstractAction {

    private final CharacterButton e;
    private final TileSquare tile;
    private final Vector2 from, to;
    private float timer = 0;

    public MovePlayerAction(CharacterButton target, TileSquare t, boolean isFast) {
        super(isFast ? 0.25f : 0.5f);
        e = target;
        from = new Vector2(target.character.pos);
        tile = t;
        to = new Vector2(t.originX, t.originY);
    }

    @Override
    protected void updateAction() {
        if(timer < 1) {
            timer += MousseAdventure.tick / baseDuration;
            if(timer >= 1) timer = 1;
            float x = Interpolation.linear.apply(from.x, to.x, timer), y = Interpolation.linear.apply(from.y, to.y, timer);
            e.character.pos.set(x, y);
        }

        if(isDone) {
            e.tile.removeEntity();
            e.tile = tile;
            e.pos = new Vector2i(tile.pos);
            e.character.pos = to;
            tile.status = TileSquare.TileStatus.ENTITY;
            tile.character = e;
            ActionHandler.bot(new FindPathAction());
        }
    }

    @Override
    protected void renderAction(SpriteBatch sb) {

    }
}
