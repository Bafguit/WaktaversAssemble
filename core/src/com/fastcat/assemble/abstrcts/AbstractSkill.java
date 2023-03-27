package com.fastcat.assemble.abstrcts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.screens.battle.TileSquare;

import static com.fastcat.assemble.MouseAdventure.battleScreen;
import static com.fastcat.assemble.MouseAdventure.game;

public abstract class AbstractSkill {

    public Sprite img;
    public int coolDown = 0;
    public int coolTime = 1;
    public int targetAmount = 1;
    public boolean hasDir = true;
    public SkillTarget target;
    public SkillDir direction;
    public Array<AbstractEntity> targets = new Array<>();
    public Vector2[] tempRange;
    protected final Vector2[] range; //기준은 위쪽

    public AbstractSkill(SkillTarget target, int range) {
        this.target = target;
        if(target == SkillTarget.SELF || target == SkillTarget.NONE) {
            hasDir = false;
        }
        this.range = new Vector2[range];
        tempRange = new Vector2[range];
        defineRange();
    }

    public final void use() {
        useSkill();
        afterUse();
    }

    public final void resetAttribute() {
        coolDown = 0;
        tempRange = new Vector2[range.length];
    }

    public final void coolDown(int c) {
        if(coolDown > 0) {
            coolDown -= c;
            if (coolDown < 0) coolDown = 0;
        }
    }

    public final void setTempRange(SkillDir dir) {
        if(dir == SkillDir.DOWN) {
            for(int i = 0; i < range.length; i++) {
                Vector2 vec = new Vector2(range[i]);
                vec.y = -vec.y;
                tempRange[i] = vec;
            }
        } else if(dir == SkillDir.LEFT) {
            for(int i = 0; i < range.length; i++) {
                Vector2 vec = new Vector2(range[i]);
                vec.set(-vec.y, vec.x);
                tempRange[i] = vec;
            }
        } else if(dir == SkillDir.RIGHT) {
            for(int i = 0; i < range.length; i++) {
                Vector2 vec = new Vector2(range[i]);
                vec.set(vec.y, vec.x);
                tempRange[i] = vec;
            }
        } else {
            for(int i = 0; i < range.length; i++) {
                tempRange[i] = new Vector2(range[i]);
            }
        }
    }

    public final void beforeUse() {
        if(target == SkillTarget.SELF) {
            targets.add(game.player);
        } else {
            setTempRange(direction);
            if (target == SkillTarget.AMOUNT) {
                for (int i = tempRange.length - 1; i >= 0; i--) {
                    Vector2 pv = new Vector2(battleScreen.player.pos);
                    pv.add(tempRange[i]);
                    int x = (int) pv.x, y = (int) pv.y;
                    if (x >= 0 && x < battleScreen.wSize && y >= 0 && y < battleScreen.hSize) {
                        TileSquare tile = battleScreen.tiles[x][y];
                        if (tile.enemy != null) {
                            targets.add(tile.enemy.entity);
                            if(targets.size == targetAmount) break;
                        }
                    }
                }
            } else if (target == SkillTarget.ALL) {
                for (int i = tempRange.length - 1; i >= 0; i--) {
                    Vector2 pv = new Vector2(battleScreen.player.pos);
                    pv.add(tempRange[i]);
                    int x = (int) pv.x, y = (int) pv.y;
                    if (x >= 0 && x < battleScreen.wSize && y >= 0 && y < battleScreen.hSize) {
                        TileSquare tile = battleScreen.tiles[x][y];
                        if (tile.enemy != null) {
                            targets.add(tile.enemy.entity);
                        }
                    }
                }
            }
        }
    }

    private void afterUse() {
        coolDown = coolTime;
    }

    protected abstract void useSkill();

    protected abstract void defineRange();

    public boolean canUse() {
        return coolDown == 0;
    }

    protected final void bot(AbstractAction action) {
        ActionHandler.bot(action);
    }

    protected final void top(AbstractAction action) {
        ActionHandler.top(action);
    }

    protected final void bot(AbstractAction action, AbstractAction pre) {
        action.preAction = pre;
        ActionHandler.bot(action);
    }

    protected final void top(AbstractAction action, AbstractAction pre) {
        action.preAction = pre;
        ActionHandler.top(action);
    }

    public enum SkillDir {
        UP, RIGHT, DOWN, LEFT
    }

    public enum SkillTarget {
        SELF, AMOUNT, ALL, NONE
    }
}
