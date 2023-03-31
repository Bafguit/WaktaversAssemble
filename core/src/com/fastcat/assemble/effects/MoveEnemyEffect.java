package com.fastcat.assemble.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractAction;
import com.fastcat.assemble.abstrcts.AbstractEffect;
import com.fastcat.assemble.screens.battle.EnemyButton;
import com.fastcat.assemble.screens.battle.TileSquare;
import com.fastcat.assemble.utils.Vector2i;

public class MoveEnemyEffect extends AbstractEffect {

    private final EnemyButton e;
    private TileSquare tile;
    private final Vector2 from;
    private Vector2 to;
    private float timer = 0;
    private final int speed;

    public MoveEnemyEffect(EnemyButton target, float duration) {
        super(duration);
        e = target;
        this.speed = target.entity.speed;
        from = new Vector2(target.entity.pos);
    }

    public void setRightNextPath() {
        int x, y;
        TileSquare t;
        Vector2i next = e.getNextPath();

        if(next.y > e.pos.y) {
            x = e.pos.x;
            y = MathUtils.clamp(e.pos.y + speed, 0, MousseAdventure.battleScreen.hSize - 1);
            t = MousseAdventure.battleScreen.tiles[x][y];
            if(t.status == TileSquare.TileStatus.NORMAL) {
                to = new Vector2(t.originX, t.originY);
                tile = MousseAdventure.battleScreen.tiles[x][y];
                if(x == next.x && y == next.y) {
                    e.nextPath();
                }
                setPrePosition();
                return;
            }
        }

        if(next.y < e.pos.y) {
            x = e.pos.x;
            y = MathUtils.clamp(e.pos.y - speed, 0, MousseAdventure.battleScreen.hSize - 1);
            t = MousseAdventure.battleScreen.tiles[x][y];
            if(t.status == TileSquare.TileStatus.NORMAL) {
                to = new Vector2(t.originX, t.originY);
                tile = MousseAdventure.battleScreen.tiles[x][y];
                if(x == next.x && y == next.y) {
                    e.nextPath();
                }
                setPrePosition();
                return;
            }
        }

        if(next.x > e.pos.x) {
            x = MathUtils.clamp(e.pos.x + speed, 0, MousseAdventure.battleScreen.hSize - 1);
            y = e.pos.y;
            t = MousseAdventure.battleScreen.tiles[x][y];
            if(t.status == TileSquare.TileStatus.NORMAL) {
                to = new Vector2(t.originX, t.originY);
                tile = MousseAdventure.battleScreen.tiles[x][y];
                if(x == next.x && y == next.y) {
                    e.nextPath();
                }
                setPrePosition();
                return;
            }
        }

        if(next.x < e.pos.x) {
            x = MathUtils.clamp(e.pos.x - speed, 0, MousseAdventure.battleScreen.hSize - 1);
            y = e.pos.y;
            t = MousseAdventure.battleScreen.tiles[x][y];
            if(t.status == TileSquare.TileStatus.NORMAL) {
                to = new Vector2(t.originX, t.originY);
                tile = MousseAdventure.battleScreen.tiles[x][y];
                if(x == next.x && y == next.y) {
                    e.nextPath();
                }
                setPrePosition();
            }
        }
    }

    private void setPrePosition() {
        //System.out.println("Moved to: " + tile.pos.x + ", " + tile.pos.y);
        e.tile.removeEntity();
        e.tile = tile;
        e.pos = tile.pos;
        tile.status = TileSquare.TileStatus.ENTITY;
        tile.enemy = e;
    }

    @Override
    protected void renderEffect(SpriteBatch sb) {
        if(to != null) {
            if (timer < 1) {
                timer += MousseAdventure.tick / baseDuration;
                if (timer >= 1) timer = 1;
                float x = Interpolation.linear.apply(from.x, to.x, timer), y = Interpolation.linear.apply(from.y, to.y, timer);
                e.entity.pos.set(x, y);
            }

            if(isDone) {
                e.entity.pos = to;
            }
        } else {
            isDone = true;
        }
    }
}
