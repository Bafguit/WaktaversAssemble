package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.SynergyHandler;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.SpriteAnimation;


public abstract class AbstractMember implements Cloneable {

    private static final String[] MIND_MASTER_SYNERGY = new String[] {
        "Guardian", "Crazy", "Expert", "Magician", "MainVocal", "Machinary",
        "Doormat", "Villain", "Kiddo", "Nobles", "Competitor", "Timid"
    };

    public final MemberData data;

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

    public void onSummon() {}

    public void onExit() {}

    public void prepare() {
        synergy = baseSynergy;
    }

    public String getName() {
        return name + (upgradeCount > 0 ? " +" + upgradeCount : "");
    }

    public void startOfTurn(boolean isPlayer) {}

    public void endOfTurn(boolean isPlayer) {}

    public int damageTake(DamageInfo info, boolean isPlayer) {
        return info.damage;
    }
    
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

    public float damageTakeMultiply(DamageInfo info, boolean isPlayer) {
        return 1f;
    }

    public void damageTaken(DamageInfo info, boolean isPlayer) {}

    protected final void bot(AbstractAction action) {
        ActionHandler.bot(action);
    }

    protected final void top(AbstractAction action) {
        ActionHandler.top(action);
    }

    protected final void next(AbstractAction action) {
        ActionHandler.next(action);
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
        return atk;
    }

    public int calculatedDef() {
        return def;
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
        System.arraycopy(baseSynergy, 0, m.synergy, 0, baseSynergy.length);
        m.setSynergy();
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
