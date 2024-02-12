package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.SynergyHandler;
import com.fastcat.assemble.interfaces.OnIncreaseGlobalDamage;
import com.fastcat.assemble.interfaces.OnIncreaseMemberDamage;
import com.fastcat.assemble.interfaces.OnIncreaseMemberDef;
import com.fastcat.assemble.synergies.Magician;
import com.fastcat.assemble.uis.SpriteAnimation;


public abstract class AbstractMember extends AbstractEntity implements Cloneable {

    private static final String[] MIND_MASTER_SYNERGY = new String[] {
        "Guardian", "Crazy", "Expert", "Magician", "MainVocal", "Machinary",
        "Doormat", "Villain", "Kiddo", "Nobles", "Timid"
    };

    public final EntityData data;

    public AbstractMember tempClone;

    public String id;
    public String name;
    public String desc;
    public String flavor;
    public TextureRegionDrawable img;
    public int upgradeCount = 0;
    public int upgradeLimit = 1;
    public int atk, baseAtk, upAtk, def, baseDef, upDef;
    public int value, baseValue, upValue, value2, baseValue2, upValue2;
    public float effect = 1.0f;
    public boolean remain = false;
    public boolean instant = false, passive = false;
    public SpriteAnimation animation;
    public final AbstractSynergy[] baseSynergy;
    public AbstractSynergy[] synergy;

    public AbstractMember(String id) {
        super(id, true);
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
        animation.addAnimation("idle");
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

        for(AbstractMember m : WakTower.game.battle.members) {
            if(m != this) m.onAfterSummon(this);
        }
    }

    public void onSummon(AbstractMember m) {}

    public void onAfterSummon(AbstractMember m) {}

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

    public boolean isEvaded() {return false;}

    protected final void bot(AbstractAction action) {
        action.bot();
    }

    protected final void top(AbstractAction action) {
        action.top();
    }

    protected final void next(AbstractAction action) {
        action.next();
    }

    protected final void set(AbstractAction action) {
        action.set();
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
            calculateValue();

            baseValue2 += upValue2;
            if(baseValue2 < 0) baseValue2 = 0;
            calculateValue2();
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

    public String getKeyValue(String key) {
        switch (key) {
            case "A":
                int a = calculatedAtk();
                String s = FontHandler.getColorKey(a < baseAtk ? "r" : a > baseAtk ? "g" :  "b");
                s += a + FontHandler.getColorKey("w");
                return s;
            case "D":
                a = calculatedDef();
                s = FontHandler.getColorKey(a < baseDef ? "r" : a > baseDef ? "g" :  "b");
                s += a + FontHandler.getColorKey("w");
                return s;
            case "V":
                return FontHandler.getColorKey("b") + value + FontHandler.getColorKey("w");
            case "X":
                return FontHandler.getColorKey("b") + value2 + FontHandler.getColorKey("w");
            default:
                return "0";
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

        float m = effect;

        for(OnIncreaseGlobalDamage g : WakTower.game.battle.turnGlobalDamage) {
            m *= g.multiplyGlobalDamage();
        }
        for(OnIncreaseMemberDamage g : WakTower.game.battle.turnMemberDamage) {
            m *= g.multiplyMemberDamage();
        }
        for(AbstractRelic r : WakTower.game.relics) {
            m *= r.multiplyAtk();
        }
        for(AbstractStatus s : WakTower.game.player.status) {
            m *= s.multiplyAtk();
        }

        for(AbstractSynergy s : synergy) {
            m *= s.muliplyEffect();
        }

        atk = (int) (a * m);
        return atk;
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

        float m = effect;

        for(OnIncreaseMemberDef g : WakTower.game.battle.turnMemberDef) {
            m *= g.multiplyMemberDef();
        }
        for(AbstractRelic r : WakTower.game.relics) {
            m *= r.multiplyDef();
        }
        for(AbstractStatus s : WakTower.game.player.status) {
            m *= s.multiplyDef();
        }

        for(AbstractSynergy s : synergy) {
            m *= s.muliplyEffect();
        }

        def = (int) (d * m);
        return def;
    }

    public int calculateValue() {
        float m = effect;
        for(AbstractSynergy s : synergy) {
            m *= s.muliplyEffect();
        }
        value = (int) (baseValue * m);
        return value;
    }

    public int calculateValue2() {
        float m = effect;
        for(AbstractSynergy s : synergy) {
            m *= s.muliplyEffect();
        }
        value2 = (int) (baseValue2 * m);
        return value2;
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
        for(AbstractSynergy s : synergy) {
            r += s.repeatAmount(this) - 1;
        }
        for(int i = 0; i < r; i++) {
            useMember();
        }
        afterUse();
    }

    protected abstract void useMember();

    public void afterUse() {}

    public boolean canUse() {
        return true;
    }

    public AbstractMember cpy() {
        AbstractMember m;
        try {
            m = (AbstractMember) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        m.animation = this.animation;
        return m;
    }

    public AbstractMember duplicate() {
        AbstractMember m = cpy();
        m.animation = this.animation.cpy();
        m.img = new TextureRegionDrawable(this.img);

        return m;
    }
}
