package com.fastcat.assemble.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEffect;
import com.fastcat.assemble.abstrcts.AbstractSkill;
import com.fastcat.assemble.screens.battle.EnemyButton;
import com.fastcat.assemble.screens.battle.TileSquare;
import com.fastcat.assemble.utils.Vector2i;

import static com.fastcat.assemble.MousseAdventure.battleScreen;

public class MoveEnemyEffect extends AbstractEffect {

    private final EnemyButton e;
    private TileSquare tile;
    private final Vector2 from;
    private Vector2 to;
    private float timer = 0;
    private final int speed;
    public boolean moveDone = false;
    public boolean pause = false;

    public MoveEnemyEffect(EnemyButton target, float duration) {
        super(duration);
        e = target;
        this.speed = target.entity.speed;
        from = new Vector2(target.entity.pos);
    }

    public void setRightNextPath() {
        for(int i = 0; i < speed; i++) {
            TileSquare t;
            Vector2i next = e.getNextPath();
            int x, y, w = Math.abs(e.pos.x - next.x), h = Math.abs(e.pos.y - next.y);
            if (h >= w) {
                if (next.y > e.pos.y) {
                    x = e.pos.x;
                    y = MathUtils.clamp(e.pos.y + 1, 0, MousseAdventure.battleScreen.hSize - 1);
                    t = MousseAdventure.battleScreen.tiles[x][y];
                    if (t.status == TileSquare.TileStatus.NORMAL) {
                        to = new Vector2(t.originX, t.originY);
                        tile = MousseAdventure.battleScreen.tiles[x][y];
                        if (x == next.x && y == next.y) {
                            e.nextPath();
                        }
                        setPrePosition();
                        return;
                    }
                }

                if (next.y < e.pos.y) {
                    x = e.pos.x;
                    y = MathUtils.clamp(e.pos.y - 1, 0, MousseAdventure.battleScreen.hSize - 1);
                    t = MousseAdventure.battleScreen.tiles[x][y];
                    if (t.status == TileSquare.TileStatus.NORMAL) {
                        to = new Vector2(t.originX, t.originY);
                        tile = MousseAdventure.battleScreen.tiles[x][y];
                        if (x == next.x && y == next.y) {
                            e.nextPath();
                        }
                        setPrePosition();
                        return;
                    }
                }

                if (next.x > e.pos.x) {
                    x = MathUtils.clamp(e.pos.x + 1, 0, MousseAdventure.battleScreen.hSize - 1);
                    y = e.pos.y;
                    t = MousseAdventure.battleScreen.tiles[x][y];
                    if (t.status == TileSquare.TileStatus.NORMAL) {
                        e.entity.updateDir(AbstractSkill.SkillDir.RIGHT);
                        to = new Vector2(t.originX, t.originY);
                        tile = MousseAdventure.battleScreen.tiles[x][y];
                        if (x == next.x && y == next.y) {
                            e.nextPath();
                        }
                        setPrePosition();
                        return;
                    }
                }

                if (next.x < e.pos.x) {
                    x = MathUtils.clamp(e.pos.x - 1, 0, MousseAdventure.battleScreen.hSize - 1);
                    y = e.pos.y;
                    t = MousseAdventure.battleScreen.tiles[x][y];
                    if (t.status == TileSquare.TileStatus.NORMAL) {
                        e.entity.updateDir(AbstractSkill.SkillDir.LEFT);
                        to = new Vector2(t.originX, t.originY);
                        tile = MousseAdventure.battleScreen.tiles[x][y];
                        if (x == next.x && y == next.y) {
                            e.nextPath();
                        }
                        setPrePosition();
                    }
                }
            } else {
                if (next.x > e.pos.x) {
                    x = MathUtils.clamp(e.pos.x + 1, 0, MousseAdventure.battleScreen.hSize - 1);
                    y = e.pos.y;
                    t = MousseAdventure.battleScreen.tiles[x][y];
                    if (t.status == TileSquare.TileStatus.NORMAL) {
                        e.entity.updateDir(AbstractSkill.SkillDir.RIGHT);
                        to = new Vector2(t.originX, t.originY);
                        tile = MousseAdventure.battleScreen.tiles[x][y];
                        if (x == next.x && y == next.y) {
                            e.nextPath();
                        }
                        setPrePosition();
                        return;
                    }
                }

                if (next.x < e.pos.x) {
                    x = MathUtils.clamp(e.pos.x - 1, 0, MousseAdventure.battleScreen.hSize - 1);
                    y = e.pos.y;
                    t = MousseAdventure.battleScreen.tiles[x][y];
                    if (t.status == TileSquare.TileStatus.NORMAL) {
                        e.entity.updateDir(AbstractSkill.SkillDir.LEFT);
                        to = new Vector2(t.originX, t.originY);
                        tile = MousseAdventure.battleScreen.tiles[x][y];
                        if (x == next.x && y == next.y) {
                            e.nextPath();
                        }
                        setPrePosition();
                    }
                }

                if (next.y > e.pos.y) {
                    x = e.pos.x;
                    y = MathUtils.clamp(e.pos.y + 1, 0, MousseAdventure.battleScreen.hSize - 1);
                    t = MousseAdventure.battleScreen.tiles[x][y];
                    if (t.status == TileSquare.TileStatus.NORMAL) {
                        to = new Vector2(t.originX, t.originY);
                        tile = MousseAdventure.battleScreen.tiles[x][y];
                        if (x == next.x && y == next.y) {
                            e.nextPath();
                        }
                        setPrePosition();
                        return;
                    }
                }

                if (next.y < e.pos.y) {
                    x = e.pos.x;
                    y = MathUtils.clamp(e.pos.y - 1, 0, MousseAdventure.battleScreen.hSize - 1);
                    t = MousseAdventure.battleScreen.tiles[x][y];
                    if (t.status == TileSquare.TileStatus.NORMAL) {
                        to = new Vector2(t.originX, t.originY);
                        tile = MousseAdventure.battleScreen.tiles[x][y];
                        if (x == next.x && y == next.y) {
                            e.nextPath();
                        }
                        setPrePosition();
                        return;
                    }
                }
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
        moveDone = true;
        e.target = e.entity.getPlayerInRange(e, e.entity.getRange());
    }

    @Override
    protected void renderEffect(SpriteBatch sb) {
        if(to != null) {
            if(duration == baseDuration) {
                e.entity.walk();
            }
            if (timer < 1) {
                timer += MousseAdventure.tick / baseDuration;
                if (timer >= 1) timer = 1;
                float x = Interpolation.linear.apply(from.x, to.x, timer), y = Interpolation.linear.apply(from.y, to.y, timer);
                e.entity.pos.set(x, y);
            }

            if(isDone) {
                e.entity.walkEnd();
                e.entity.pos = to;
            }
        } else {
            isDone = true;
        }
    }
}
