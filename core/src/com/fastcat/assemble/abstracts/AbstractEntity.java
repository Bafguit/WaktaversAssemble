package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.interfaces.OnStatusUpdated;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.SpriteAnimation;
import com.fastcat.assemble.utils.SpriteAnimation.SpriteAnimationType;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class AbstractEntity {

    protected final EntityData data;
    public final String id, name, desc;
    public final boolean isPlayer;
    public final SpriteAnimation animation;

    public int health, maxHealth, block, barrier;
    public boolean isDie, isDead;
    public LinkedList<AbstractStatus> status = new LinkedList<>();
    public Array<OnStatusUpdated> statusUpdatedListener = new Array<>();

    public AbstractEntity(String id, boolean isPlayer) {
        this.id = id;
        data = DataHandler.getInstance().entityData.get(id);
        name = data.name;
        desc = data.desc;
        health = maxHealth = data.health;
        block = barrier = 0;
        animation = data.animation;
        this.isPlayer = isPlayer;
    }

    public final void applyStatus(AbstractStatus s) {
        Iterator<AbstractStatus> it = status.iterator();
        while (it.hasNext()) {
            AbstractStatus t = it.next();
            if(t.id.equals(s.id)) {
                if(t.hasAmount) {
                    t.apply(s.amount);
                    if(t.amount == 0 || (t.amount < 0 && !t.canGoNegative)) {
                        it.remove();
                        t.onRemove();
                        if(statusUpdatedListener.size > 0) {
                            for(OnStatusUpdated listener : statusUpdatedListener) {
                            listener.onStatusInitial(s);
                            }
                        }
                    }
                }
                return;
            }
        }
        s.owner = this;
        status.add(s);
        s.onInitial();
        if(statusUpdatedListener.size > 0) {
            for(OnStatusUpdated listener : statusUpdatedListener) {
                listener.onStatusInitial(s);
            }
        }
    }

    public final void gainBlock(int amount) {
        if(amount > 0) {
            block += 0;
            if(isPlayer) {
                for(AbstractRelic item : WakTower.game.relics) {
                    item.onGainedBlock(amount);
                }
                for(AbstractMember c : WakTower.game.battle.members) {
                    c.onGainedBlock(amount);
                }
            }
            for(AbstractStatus s : status) {
                s.onGainedBlock(amount);
            }
        }
    }

    public final void gainBarrier(int amount) {
        if(amount > 0) {
            barrier += amount;
            if(isPlayer) {
                for(AbstractRelic item : WakTower.game.relics) {
                    item.onGainBarrier(amount);
                }
                for(AbstractMember c : WakTower.game.battle.members) {
                    c.onGainBarrier(amount);
                }
            }
            for(AbstractStatus s : status) {
                s.onGainBarrier(amount);
            }
        }
    }

    public final void takeDamage(DamageInfo info) {
        for(AbstractRelic item : WakTower.game.relics) {
            info.damage = item.damageTake(info, isPlayer);
        }
        for(AbstractMember c : WakTower.game.battle.members) {
            info.damage = c.damageTake(info, isPlayer);
        }
        for(AbstractStatus s : status) {
            info.damage = s.damageTake(info);
        }
        float td = 1f;
        for(AbstractRelic item : WakTower.game.relics) {
            td *= item.damageTakeMultiply(info, isPlayer);
        }
        for(AbstractMember c : WakTower.game.battle.members) {
            td *= c.damageTakeMultiply(info, isPlayer);
        }
        for(AbstractStatus s : status) {
            td *= s.damageTakeMultiply(info);
        }
        info.damage = (int)(info.damage * td);
        if(info.damage <= 0) return;
        if(block > 0) {
            if(info.damage <= block) {
                //todo 이펙트 (damage)
                block -= info.damage;
                return;
            } else {
                //todo 이펙트 (block)
                info.damage -= block;
                block = 0;
            }
        }
        if(barrier > 0) {
            if(info.damage <= barrier) {
                //todo 이펙트 (damage)
                barrier -= info.damage;
                return;
            } else {
                //todo 이펙트 (barrier)
                info.damage -= barrier;
                barrier = 0;
            }
        }
        //todo 이펙트 (damage)
        health -= info.damage;
        if(health < 0) die();
        for(AbstractRelic item : WakTower.game.relics) {
            item.damageTaken(info, isPlayer);
        }
        for(AbstractMember c : WakTower.game.battle.members) {
            c.damageTaken(info, isPlayer);
        }
        if(health > 0) {
            for(AbstractStatus s : status) {
                s.damageTaken(info);
            }
        }
    }

    public final void die() {}

    public boolean isAlive() {
        return !isDie && !isDead;
    }

    public static class EntityData {
        public final String id;
        public final String name;
        public final String desc;
        public final int health;
        public final SpriteAnimation animation;

        public EntityData(String id, JsonValue json) {
            this.id = id;
            name = json.getString("name");
            desc = json.getString("desc");
            health = json.getInt("health");
            animation = new SpriteAnimation(id, SpriteAnimationType.entity);
        }

        private EntityData(String id, String name, String desc, int health, SpriteAnimation anim) {
            this.id = id;
            this.name = name;
            this.desc = desc;
            this.health = health;
            this.animation = anim.cpy();
        }

        public EntityData cpy() {
            return new EntityData(id, name, desc, health, animation);
        }
    }
}
