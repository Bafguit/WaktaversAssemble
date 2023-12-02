package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.utils.SpriteAnimation;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class AbstractEntity {

    protected final EntityData data;
    public final String id, name, desc;
    public final boolean isPlayer;
    public final SpriteAnimation animation;

    public int health, maxHealth, block, barrier;
    public LinkedList<AbstractStatus> status = new LinkedList<>();

    public AbstractEntity(String id, boolean isPlayer) {
        this.id = id;
        data = DataHandler.entityData.get(id).cpy();
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
                        t.onRemove();
                        it.remove();
                    }
                }
                return;
            }
        }
        status.add(s);
        s.onInitial();
    }

    public final void gainBlock(int amount) {
        //todo 방어도 얻는 코드
    }

    public final void gainBarrier(int amount) {
        //todo 보호막 얻는 코드
    }

    public final void takeDamage(DamageInfo info) {
        for(AbstractRelic item : WakTower.game.items) {
            info.damage = item.damageTake(info, isPlayer);
        }
        for(AbstractChar c : WakTower.battle.chars) {
            info.damage = c.damageTake(info, isPlayer);
        }
        for(AbstractStatus s : status) {
            info.damage = s.damageTake(info);
        }
        float td = 1f;
        for(AbstractRelic item : WakTower.game.items) {
            td *= item.damageTakeMultiply(info, isPlayer);
        }
        for(AbstractChar c : WakTower.battle.chars) {
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
        for(AbstractRelic item : WakTower.game.items) {
            item.damageTaken(info, isPlayer);
        }
        for(AbstractChar c : WakTower.battle.chars) {
            c.damageTaken(info, isPlayer);
        }
        if(health > 0) {
            for(AbstractStatus s : status) {
                s.damageTaken(info);
            }
        }
    }

    public final void die() {}

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
            animation = new SpriteAnimation();
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
