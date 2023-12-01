package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
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

    public int health, maxHealth;
    public LinkedList<AbstractStatus> status = new LinkedList<>();

    public AbstractEntity(String id, boolean isPlayer) {
        this.id = id;
        data = DataHandler.entityData.get(id).cpy();
        name = data.name;
        desc = data.desc;
        health = maxHealth = data.health;
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
