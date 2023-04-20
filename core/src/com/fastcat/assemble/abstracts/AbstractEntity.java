package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.AnimationState;
import com.fastcat.assemble.effects.DieEffect;
import com.fastcat.assemble.effects.HitEffect;
import com.fastcat.assemble.effects.UpColorTextEffect;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.EffectHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.utils.SpineAnimation;
import com.fastcat.assemble.utils.Vector2i;

import java.util.LinkedList;

public abstract class AbstractEntity {

    public final Color baseColor = new Color(1, 1, 1, 1);
    public final Color animColor = new Color(1, 1, 1, 1);

    //local static
    public String id;
    public String name;
    public String desc;
    public EntityRarity rarity;
    public Sprite img;
    public Vector2 pos;
    public Vector2 animPos;
    public Vector2i tilePos;

    protected TextureAtlas atlas;
    protected FileHandle skeleton;
    public SpineAnimation animation;
    public boolean isFlip = false;

    public int uuid;
    public int baseMaxHealth;
    public int baseAttack;
    public int baseDefense;
    public int baseMagicRes;
    public boolean isPlayer;
    public boolean isDie;
    public boolean isDead;
    public boolean invisible = false;

    public LinkedList<AbstractStatus> status = new LinkedList<>();

    //temporary
    public int speed = 1;
    public int attack;
    public int defense;
    public int magicRes;
    public int health;
    public int maxHealth;
    public int shield = 0;
    public int physShield = 0;
    public int magicShield = 0;

    public AbstractEntity(String id, int attack, int health, int def, int res, EntityRarity rarity) {
        this.id = id;
        this.attack = this.baseAttack = attack;
        this.health = maxHealth = baseMaxHealth = health;
        this.defense = this.baseDefense = def;
        this.magicRes = this.baseMagicRes = res;
        this.rarity = rarity;
        pos = new Vector2();
        animPos = new Vector2(-10000, -10000);
        img = FileHandler.character.get(id);
        setAnimation();
    }

    protected void setAnimation() {
        atlas = FileHandler.atlas.get(id);
        skeleton = FileHandler.skeleton.get(id);
        animation = new SpineAnimation(atlas, skeleton);
        animation.resetAnimation();
    }

    public final void resetAttributes() {
        health = maxHealth = baseMaxHealth;
        status.clear();
        defense = baseDefense;
        magicRes = baseMagicRes;
        attack = baseAttack;
        shield = 0;
        physShield = 0;
        magicShield = 0;
        isDie = false;
        isDead = false;
        animation.resetAnimation();
        animColor.set(baseColor);
    }

    public void render(SpriteBatch sb) {
        if (!isDead) {
            Color c = sb.getColor();
            sb.setColor(animColor);
            animation.render(sb, pos.x, pos.y - 35, isFlip);
            sb.setColor(c);
        }
    }

    public final boolean isAlive() {
        return !isDead && !isDie;
    }

    public void attack(Array<AbstractEntity> target, DamageInfo info) {
        if (target.size > 0) {
            for (AbstractEntity t : target) {
                if (t.isAlive()) {
                    t.takeDamage(info);
                }
            }
        }
    }

    public void afterAttack() {
        for(AbstractStatus s : status) {
            s.onAfterAttack();
        }
    }

    public void attackAndHeal(Array<AbstractEntity> target, DamageInfo info, int heal) {
        if(target.size > 0) {
            for(AbstractEntity t : target) {
                if(t.isAlive()) {
                    t.takeDamage(info);
                    heal(heal);
                }
            }
        }
    }

    public int calculatedAttack() {
        int atk = baseAttack;
        int mod = 100;

        for(AbstractStatus s : status) {
            atk += s.addFixedAttack();
        }

        for(AbstractStatus s : status) {
            mod += s.increaseAttack();
        }

        atk *= (mod * 0.01f);

        for(AbstractStatus s : status) {
            atk += s.addAttack();
        }

        return atk;
    }

    public int calculatedDefense() {
        int atk = baseDefense;
        int mod = 100;

        for(AbstractStatus s : status) {
            atk += s.addFixedDefense();
        }

        for(AbstractStatus s : status) {
            mod += s.increaseDefense();
        }

        atk *= (mod * 0.01f);

        for(AbstractStatus s : status) {
            atk += s.addDefense();
        }

        return atk;
    }

    public int calculatedMagicRes() {
        int atk = baseMagicRes;
        int mod = 100;

        for(AbstractStatus s : status) {
            atk += s.addFixedMagicRes();
        }

        for(AbstractStatus s : status) {
            mod += s.increaseMagicRes();
        }

        atk *= (mod * 0.01f);

        for(AbstractStatus s : status) {
            atk += s.addMagicRes();
        }

        return atk;
    }

    public void takeDamage(DamageInfo info) {
        DamageType type = info.type;
        int damage = info.damage;
        if(type == DamageType.PHYSICAL) {
            damage = Math.max(damage - calculatedDefense(), (int) (damage * 0.05f));
        } else if(type == DamageType.MAGIC) {
            damage = (int) Math.max(damage * ((100 - calculatedMagicRes()) * 0.01f), damage * 0.05f);
        } else if(damage < 0) {
            damage = 0;
        }
        if(health - damage <= 0) {
            health = 0;
            die();
        } else {
            health -= damage;
            EffectHandler.add(new HitEffect(this));
        }
        EffectHandler.add(new UpColorTextEffect(pos.x, pos.y, damage, Color.SCARLET));
    }

    public void heal(int amount) {
        health = Math.min(health + amount, maxHealth);
        EffectHandler.add(new UpColorTextEffect(pos.x, pos.y, amount, Color.CHARTREUSE));
    }

    public void die() {
        ActionHandler.add(new DieEffect(this));
        onDead();
    }

    public void onDead() {

    }

    public void applyStatus(AbstractStatus s) {
        boolean isDone = false;
        for(AbstractStatus st : status) {
            if(st.id.equals(s.id)) {
                st.apply(s.amount, s.turnLeft);
                isDone = true;
                break;
            }
        }

        if(!isDone) {
            s.owner = this;
            status.add(s);
            s.onInitial();
        }
    }

    public void updateDir(AbstractSkill.SkillDir dir) {
        isFlip = dir == AbstractSkill.SkillDir.LEFT || dir == AbstractSkill.SkillDir.DOWN;
    }

    public static class DamageInfo {
        public DamageType type;
        public int damage;

        public DamageInfo(int damage, DamageType type) {
            this.type = type;
            this.damage = damage;
        }
    }

    public void removeStatus(String id) {
        for(AbstractStatus s : status) {
            if(s.id.equals(id)) {
                status.remove(s);
                break;
            }
        }
    }

    public void walkEnd() {
        animation.resetAnimation();
    }

    public void walk() {
        animation.set("Move", true);
    }

    public void attackAnimation(int target, AnimationState.AnimationStateAdapter adapter) {
        animation.setAndIdle("Attack", adapter);
    }

    public void dieAnimation(AnimationState.AnimationStateAdapter adapter) {
        animation.set("Die", adapter, false);
    }

    public void removeStatus(AbstractStatus s) {
        status.remove(s);
    }

    public enum DamageType {
        PHYSICAL, MAGIC, TRUE
    }

    public enum EntityRarity {
        OPERATOR,
        NORMAL,
        ELITE,
        LEADER
    }
}
