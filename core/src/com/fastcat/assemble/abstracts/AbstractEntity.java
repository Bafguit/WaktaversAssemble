package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.effects.UpColorTextEffect;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.EffectHandler;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.interfaces.OnHealthUpdated;
import com.fastcat.assemble.interfaces.OnStatusUpdated;
import com.fastcat.assemble.synergies.Badass;
import com.fastcat.assemble.synergies.Doormat;
import com.fastcat.assemble.synergies.Kiddo;
import com.fastcat.assemble.synergies.Nunna;
import com.fastcat.assemble.synergies.OldMan;
import com.fastcat.assemble.uis.SpriteAnimation;
import com.fastcat.assemble.uis.SpriteAnimation.SpriteAnimationType;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.HealthBar;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

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
    public Array<OnHealthUpdated> healthUpdatedListener = new Array<>();

    public HealthBar healthBar;

    public AbstractEntity(String id, boolean isPlayer) {
        this.id = id;
        data = (isPlayer ? DataHandler.getInstance().memberData : DataHandler.getInstance().enemyData).get(id);
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
                                listener.onStatusRemoved(s);
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
            block += amount;
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

    public final void heal(int amount) {
        if(amount > 0) {
            //EffectHandler.add(new UpColorTextEffect(animation.pos.x, animation.pos.y + 150 * InputHandler.scaleY, -amount, Color.LIME));
            health += amount;
            if(health > maxHealth) health = maxHealth;
            if(healthUpdatedListener.size > 0) {
                for(OnHealthUpdated o : healthUpdatedListener) {
                    o.onHealthUpdated(amount);
                }
            }
            if(healthBar != null) healthBar.onHealthUpdated(amount);
            if(isPlayer) {
                for(AbstractRelic item : WakTower.game.relics) {
                    item.onHealed(amount);
                }
                for(AbstractMember c : WakTower.game.battle.members) {
                    c.onHealed(amount);
                }
            }
            for(AbstractStatus s : status) {
                s.onHealed(amount);
            }
        }
    }

    public final int takeDamage(DamageInfo info) {
        if(isPlayer) {
            boolean kiddo = Kiddo.getInstance().isEvaded();
            boolean bj = false;
            for(AbstractMember c : WakTower.game.battle.members) {
                if(c.isEvaded()) {
                    bj = true;
                    break;
                }
            }
            if(kiddo || bj) {
                //evade effect
                return 0;
            }
        }
        boolean fromEnemy = info.source != null && !info.source.isPlayer && isPlayer;
        if(info.type != DamageType.LOSE) {
            for(AbstractRelic item : WakTower.game.relics) {
                info.damage = item.damageTake(info);
            }
            if(fromEnemy) info.damage = Doormat.getInstance().damageTake(info);
            
            info.damage = damageTake(info);

            for(AbstractStatus s : status) {
                info.damage = s.damageTake(info);
            }
            if(info.damage <= 0) return 0;
            float td = 1f;
            for(AbstractRelic item : WakTower.game.relics) {
                td *= item.damageTakeMultiply(info);
            }
            if(!isPlayer) td *= Nunna.getInstance().damageMultiply();
            else if(fromEnemy) td *= OldMan.getInstance().damageMultiply();
            td *= damageTakeMultiply(info);
            for(AbstractStatus s : status) {
                td *= s.damageTakeMultiply(info);
            }
            info.damage = (int)(info.damage * td);
            if(info.damage <= 0) return 0;
            boolean ignore = !isPlayer && info.source != null && info.source.isPlayer && Badass.getInstance().ignoreDef(info);
            if(!ignore) {
                if(block > 0) {
                    if(info.damage <= block) {
                        EffectHandler.add(new UpColorTextEffect(animation.pos.x, animation.pos.y + 150 * InputHandler.scaleY, -info.damage, Color.CYAN));
                        block -= info.damage;
                        return 0;
                    } else {
                        EffectHandler.add(new UpColorTextEffect(animation.pos.x, animation.pos.y + 150 * InputHandler.scaleY, -block, Color.CYAN));
                        info.damage -= block;
                        block = 0;
                    }
                }
                if(barrier > 0) {
                    if(info.damage <= barrier) {
                        EffectHandler.add(new UpColorTextEffect(animation.pos.x, animation.pos.y + 150 * InputHandler.scaleY, -info.damage, Color.SALMON));
                        barrier -= info.damage;
                        return 0;
                    } else {
                        EffectHandler.add(new UpColorTextEffect(animation.pos.x, animation.pos.y + 150 * InputHandler.scaleY, -barrier, Color.SALMON));
                        info.damage -= barrier;
                        barrier = 0;
                    }
                }
            }
        }
        //EffectHandler.add(new UpColorTextEffect(animation.pos.x, animation.pos.y + 150 * InputHandler.scaleY, -info.damage, Color.GOLD));
        animation.setAnimation("hit");
        animation.addAnimation("idle");
        health -= info.damage;
        if(health <= 0) health = 0;
        if(healthUpdatedListener.size > 0) {
            for(OnHealthUpdated o : healthUpdatedListener) {
                o.onHealthUpdated(-info.damage);
            }
        }
        if(healthBar != null) healthBar.onHealthUpdated(-info.damage);
        if(health <= 0) die();

        if(info.type != DamageType.LOSE) {
            for(AbstractRelic item : WakTower.game.relics) {
                item.onDamage(info, this);
            }
            if(info.source != null) {
                info.source.onDamage(info, this);
                for(AbstractStatus s : status) {
                    s.onDamage(info, this);
                }
            }
            if(health > 0) {
                this.damageTaken(info);
                for(AbstractStatus s : status) {
                    s.damageTaken(info);
                }
            }
        }

        return info.damage;
    }

    public final void loseHealth(int amount) {
        EffectHandler.add(new UpColorTextEffect(animation.pos.x, animation.pos.y + 150 * InputHandler.scaleY, -amount, Color.WHITE));
        health -= amount;
        if(healthUpdatedListener.size > 0) {
            for(OnHealthUpdated o : healthUpdatedListener) {
                o.onHealthUpdated(-amount);
            }
        }
        if(healthBar != null) healthBar.onHealthUpdated(-amount);
        if(health < 0) die();
    }

    public int damageTake(DamageInfo info) {
        return info.damage;
    }
    
    public void onAttack(DamageInfo info, Array<AbstractEntity> target) {}

    public void onDamage(DamageInfo info, AbstractEntity target) {}
    
    public int onGainBlock(AbstractEntity target, int amount) {
        return amount;
    }
    
    public int onGainBarrier(int amount) {
        return amount;
    }
    
    public void onGainedBlock(int amount) {}
    
    public void onGainedBarrier(int amount) {}
    
    public int onHeal(int amount) {
        return amount;
    }
    
    public void onHealed(int amount) {}

    public float damageTakeMultiply(DamageInfo info) {
        return 1f;
    }

    public void damageTaken(DamageInfo info) {}

    public final void die() {
        
    }

    public boolean isAlive() {
        return !isDie && !isDead;
    }

    public static class EntityData {
        public final String id;
        public final String name;
        public final String desc;
        public final int health;
        public final SpriteAnimation animation;

        public String[] synergy;
        public String flavor;

        public EntityData(String id, SpriteAnimationType type, JsonValue json) {
            this.id = id;
            name = json.getString("name");
            desc = json.getString("desc");
            health = json.getInt("health");
            animation = new SpriteAnimation(id, type);

            JsonValue j = json.get("synergy");
            if(j != null) synergy = j.asStringArray();
            else synergy = new String[0];

            flavor = json.getString("flavor", "");
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
