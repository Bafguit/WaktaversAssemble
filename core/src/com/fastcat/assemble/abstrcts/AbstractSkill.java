package com.fastcat.assemble.abstrcts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.StringHandler;
import com.fastcat.assemble.screens.battle.TileSquare;
import com.fastcat.assemble.strings.SkillString;
import com.fastcat.assemble.utils.Vector2i;

import static com.fastcat.assemble.MousseAdventure.battleScreen;
import static com.fastcat.assemble.MousseAdventure.game;

public abstract class AbstractSkill {

    public final String id;
    public final SkillString.SkillData data;
    public AbstractUI.SubText subText;
    public String name;
    public String desc;
    public Sprite img;
    public int coolDown = 0;
    public int coolTime = 1;
    public int targetAmount = 1;
    public boolean hasDir = true;
    public SkillTarget target;
    public SkillDir direction;
    public Array<AbstractEntity> targets = new Array<>();
    public TileSquare toTile;
    public Vector2i[] tempRange;
    protected final Vector2i[] range; //기준은 위쪽

    public AbstractSkill(String id, SkillTarget target, int range) {
        this.id = id;
        data = StringHandler.skillString.get(id);
        name = data.NAME;
        desc = data.DESC;
        subText = new AbstractUI.SubText(name, desc);
        img = FileHandler.skill.get(id);
        this.target = target;
        if(target == SkillTarget.SELF || target == SkillTarget.NONE) {
            hasDir = false;
        }
        this.range = new Vector2i[range];
        tempRange = new Vector2i[range];
        defineRange();
    }

    public final void use() {
        useSkill();
        afterUse();
    }

    public final void resetAttribute() {
        coolDown = 0;
        tempRange = new Vector2i[range.length];
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
                Vector2i vec = new Vector2i(range[i]);
                vec.y = -vec.y;
                tempRange[i] = vec;
            }
        } else if(dir == SkillDir.LEFT) {
            for(int i = 0; i < range.length; i++) {
                Vector2i vec = new Vector2i(range[i]);
                vec.set(-vec.y, vec.x);
                tempRange[i] = vec;
            }
        } else if(dir == SkillDir.RIGHT) {
            for(int i = 0; i < range.length; i++) {
                Vector2i vec = new Vector2i(range[i]);
                vec.set(vec.y, vec.x);
                tempRange[i] = vec;
            }
        } else {
            for(int i = 0; i < range.length; i++) {
                tempRange[i] = new Vector2i(range[i]);
            }
        }
    }

    public final void beforeUse() {
        targets.clear();
        if(target == SkillTarget.SELF) {
            targets.add(game.player);
        } else {
            setTempRange(direction);
            if (target == SkillTarget.AMOUNT) {
                for (int i = tempRange.length - 1; i >= 0; i--) {
                    Vector2i pv = new Vector2i(battleScreen.player.pos);
                    pv.add(tempRange[i]);
                    int x = pv.x, y = pv.y;
                    if (x >= 0 && x < battleScreen.wSize && y >= 0 && y < battleScreen.hSize) {
                        TileSquare tile = battleScreen.tiles[x][y];
                        if (tile.enemy != null && !tile.enemy.entity.invisible) {
                            targets.add(tile.enemy.entity);
                            if(targets.size == targetAmount) break;
                        }
                    }
                }
            } else if (target == SkillTarget.ALL) {
                for (int i = tempRange.length - 1; i >= 0; i--) {
                    Vector2i pv = new Vector2i(battleScreen.player.pos);
                    pv.add(tempRange[i]);
                    int x = pv.x, y = pv.y;
                    if (x >= 0 && x < battleScreen.wSize && y >= 0 && y < battleScreen.hSize) {
                        TileSquare tile = battleScreen.tiles[x][y];
                        if (tile.enemy != null && !tile.enemy.entity.invisible) {
                            targets.add(tile.enemy.entity);
                        }
                    }
                }
            } else if (target == SkillTarget.MOVE) {
                for (int i = tempRange.length - 1; i >= 0; i--) {
                    Vector2i pv = new Vector2i(battleScreen.player.pos);
                    pv.add(tempRange[i]);
                    int x = pv.x, y = pv.y;
                    if (x >= 0 && x < battleScreen.wSize && y >= 0 && y < battleScreen.hSize) {
                        TileSquare tile = battleScreen.tiles[x][y];
                        if (tile.status == TileSquare.TileStatus.NORMAL) {
                            toTile = tile;
                            break;
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
        SELF, AMOUNT, ALL, NONE, MOVE
    }
}
