package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.SynergyHandler;
import com.fastcat.assemble.interfaces.OnIncreaseGlobalDamage;
import com.fastcat.assemble.interfaces.OnIncreaseMemberDamage;
import com.fastcat.assemble.interfaces.OnIncreaseMemberDef;
import com.fastcat.assemble.synergies.Magician;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.SpriteAnimation;


public abstract class AbstractMember implements Cloneable {

    private static final String[] MIND_MASTER_SYNERGY = new String[] {
        "Guardian", "Crazy", "Expert", "Magician", "MainVocal", "Machinary",
        "Doormat", "Villain", "Kiddo", "Nobles", "Timid"
    };

    public final MemberData data;

    public AbstractMember tempClone;

    public String id;
    public String name;
    public String desc;
    public String flavor;
    public Sprite img;
    public int upgradeCount = 0;
    public int upgradeLimit = 1;
    public int atk, baseAtk, upAtk, def, baseDef, upDef;
    public int value, baseValue, upValue, value2, baseValue2, upValue2;
    public boolean remain = false;
    public boolean instant = false, passive = false;
    public SpriteAnimation animation;
    public final AbstractSynergy[] baseSynergy;
    public AbstractSynergy[] synergy;

    public AbstractMember(String id) {
        this.id = id;
        data = DataHandler.getInstance().memberData.get(id);
        name = data.name;
        desc = data.desc;
        flavor = data.flavor;
        img = FileHandler.getMember(id);
        animation = data.animation.cpy();
        synergy = baseSynergy = new AbstractSynergy[data.synergy.length];
        setSynergy();
        setAtk(0, 0);
        setDef(0, 0);
        setValue(0, 0);
        setValue2(0, 0);
    }

    private final void setSynergy() {
        for(int i = 0; i < data.synergy.length; i++) {
            baseSynergy[i] = SynergyHandler.getSynergyInstance(data.synergy[i]);
        }
    }

    protected final void setAtk(int base, int up) {
        atk = baseAtk = base;
        upAtk = up;
    }

    protected final void setDef(int base, int up) {
        def = baseDef = base;
        upDef = up;
    }

    protected final void setValue(int base, int up) {
        value = baseValue = base;
        upValue = up;
    }

    protected final void setValue2(int base, int up) {
        value2 = baseValue2 = base;
        upValue2 = up;
    }

    public void summon() {
        animation.setAnimation("summon");
        for(AbstractSynergy s : synergy) {
            s.addMember(this);
        }
        onSummon();
    }
    
    public void onDrawn() {
        for(int i = 0; i < synergy.length; i++) {
            AbstractSynergy s = synergy[i];
            if(s.id.equals("MindMaster")) {
                Array<String> ss = new Array<>();
                for(String k : MIND_MASTER_SYNERGY) {
                    boolean b = true;
                    for(AbstractSynergy sn : synergy) {
                        if(sn.id.equals(k)) {
                            b = false;
                            break;
                        }
                    }
                    if(b) ss.add(k);
                }
                synergy[i] = SynergyHandler.getSynergyInstance(ss.get(WakTower.game.publicRandom.random(0, ss.size - 1)));
            }
        }
    }

    public final void onSummon() {
        for(AbstractMember m : WakTower.game.battle.members) {
            m.newTemp();
        }
        for(AbstractRelic r : WakTower.game.relics) {
            r.onSummon(this);
        }
        for(AbstractSynergy s : WakTower.game.battle.synergy) {
            s.onSummon(this);
        }
        for(AbstractMember m : WakTower.game.battle.members) {
            if(m != this) m.onSummon(this);
        }
        for(AbstractStatus s : WakTower.game.player.status) {
            s.onSummon(this);
        }
        onSummoned();
    }

    public void onSummon(AbstractMember m) {}

    protected void onSummoned() {}

    public void onExit() {}

    public void prepare() {
        synergy = baseSynergy;
        newTemp();
    }

    public void newTemp() {
        tempClone = this.cpy();
    }

    public void upgradeTemp(int amount) {
        for(int i = 0; i < amount; i++) {
            tempClone.upgrade(false);
        }
    }

    public String getName() {
        return name + (upgradeCount > 0 ? " +" + upgradeCount : "");
    }

    public void startOfTurn(boolean isPlayer) {}

    public void endOfTurn(boolean isPlayer) {}

    public int damageTake(DamageInfo info) {
        return info.damage;
    }
    
    public void onAttack(DamageInfo info, Array<AbstractEntity> target) {}

    public void onDamage(DamageInfo info, AbstractEntity target) {}
    
    public int onGainBlock(int amount) {
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

    protected final void bot(AbstractAction action) {
        ActionHandler.bot(action);
    }

    protected final void top(AbstractAction action) {
        ActionHandler.top(action);
    }

    protected final void next(AbstractAction action) {
        ActionHandler.next(action);
    }

    public void upgrade() {
        if(upgradeCount < upgradeLimit) {
            upgradeCount++;

            baseAtk += upAtk;
            if(baseAtk < 0) baseAtk = 0;

            baseDef += upDef;
            if(baseDef < 0) baseDef = 0;

            baseValue += upValue;
            if(baseValue < 0) baseValue = 0;
            value = baseValue;

            baseValue2 += upValue2;
            if(baseValue2 < 0) baseValue2 = 0;
            value2 = baseValue2;
        }
    }

    public void upgrade(boolean hasLimit) {
        if(!hasLimit || upgradeCount < upgradeLimit) {
            upgradeCount++;
            
            baseAtk += upAtk;
            if(baseAtk < 0) baseAtk = 0;

            baseDef += upDef;
            if(baseDef < 0) baseDef = 0;

            baseValue += upValue;
            if(baseValue < 0) baseValue = 0;
            value = baseValue;

            baseValue2 += upValue2;
            if(baseValue2 < 0) baseValue2 = 0;
            value2 = baseValue2;
        }
    }

    public int getKeyValue(String key) {
        switch (key) {
            case "A":
                return calculatedAtk();
            case "D":
                return calculatedDef();
            case "V":
                return value;
            case "X":
                return value2;
            default:
                return 0;
        }
    }

    public int calculatedAtk() {
        int a = baseAtk;

        for(OnIncreaseGlobalDamage g : WakTower.game.battle.turnGlobalDamage) {
            a += g.increaseGlobalDamage();
        }
        for(OnIncreaseMemberDamage g : WakTower.game.battle.turnMemberDamage) {
            a += g.increaseMemberDamage();
        }
        for(AbstractRelic r : WakTower.game.relics) {
            a = r.calculateAtk(a);
        }
        Magician mgc = Magician.getInstance();
        if(mgc.grade > 0) a += mgc.increaseMemberDamage();
        for(AbstractStatus s : WakTower.game.player.status) {
            a = s.calculateAtk(a);
        }

        for(OnIncreaseGlobalDamage g : WakTower.game.battle.turnGlobalDamage) {
            a *= g.multiplyGlobalDamage();
        }
        for(OnIncreaseMemberDamage g : WakTower.game.battle.turnMemberDamage) {
            a *= g.multiplyMemberDamage();
        }
        for(AbstractRelic r : WakTower.game.relics) {
            a *= r.multiplyAtk();
        }
        for(AbstractStatus s : WakTower.game.player.status) {
            a *= s.multiplyAtk();
        }

        atk = a;
        return a;
    }

    public int calculatedDef() {
        int d = baseDef;
        
        for(OnIncreaseMemberDef g : WakTower.game.battle.turnMemberDef) {
            d += g.increaseMemberDef();
        }
        for(AbstractRelic r : WakTower.game.relics) {
            d = r.calculateDef(d);
        }
        for(AbstractStatus s : WakTower.game.player.status) {
            d = s.calculateDef(d);
        }

        for(OnIncreaseMemberDef g : WakTower.game.battle.turnMemberDef) {
            d *= g.multiplyMemberDef();
        }
        for(AbstractRelic r : WakTower.game.relics) {
            d *= r.multiplyDef();
        }
        for(AbstractStatus s : WakTower.game.player.status) {
            d *= s.multiplyDef();
        }

        def = d;
        return d;
    }

    public boolean hasSynergy(AbstractSynergy s) {
        for(AbstractSynergy sy : synergy) {
            if(sy.id.equals(s.id)) return true;
        }
        return false;
    }

    public boolean hasSynergy(String id) {
        for(AbstractSynergy sy : synergy) {
            if(sy.id.equals(id)) return true;
        }
        return false;
    }

    public final void use() {
        int r = 1;
        for(AbstractRelic rl : WakTower.game.relics) {
            r += rl.repeatAmount(this) - 1;
        }
        for(AbstractSynergy s : WakTower.game.battle.synergy) {
            r += s.repeatAmount(this) - 1;
        }
        for(int i = 0; i < r; i++) {
            useMember();
        }
        afterUse();
    }

    protected abstract void useMember();

    public void afterUse() {}

    public AbstractMember cpy() {
        AbstractMember m;
        try {
            m = (AbstractMember) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        m.img = FileHandler.getMember(id);
        m.animation = m.animation.cpy();
        return m;
    }

    public static class MemberData {
        public final String id;
        public final String name;
        public final String desc;
        public final String flavor;
        public final SpriteAnimation animation;
        public final String[] synergy;

        public MemberData(String id, JsonValue json) {
            this.id = id;
            name = json.getString("name");
            desc = json.getString("desc");
            flavor = json.getString("flavor");
            animation = DataHandler.getInstance().animation.get(id);
            synergy = json.get("synergy").asStringArray();
        }
    }
}
