package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.SynergyHandler;
import com.fastcat.assemble.interfaces.OnIncreaseGlobalDamage;
import com.fastcat.assemble.interfaces.OnIncreaseMemberDamage;
import com.fastcat.assemble.interfaces.OnIncreaseMemberDef;
import com.fastcat.assemble.synergies.Magician;


public abstract class AbstractMember extends AbstractEntity implements Cloneable {

    private static final String WHITE = FontHandler.getColorKey("w");
    private static final String BLUE = FontHandler.getColorKey("b");
    private static final String RED = FontHandler.getColorKey("r");
    private static final String GREEN = FontHandler.getColorKey("g");

    private static final String[] MIND_MASTER_SYNERGY = new String[] {
        "Guardian", "Crazy", "Expert", "Magician", "MainVocal", "Machinary",
        "Doormat", "Villain", "Kiddo", "Nobles", "Timid"
    };

    public AbstractMember tempClone;

    public String flavor;
    public TextureRegionDrawable img;
    public int upgradeCount = 0;
    public int atk, baseAtk, upAtk, def, baseDef, upDef;
    public int value, baseValue, upValue, value2, baseValue2, upValue2;
    public float effect = 1.0f;
    public boolean remain = false;
    public boolean instant = false, passive = false;
    public final AbstractSynergy[] baseSynergy;
    public AbstractSynergy[] synergy;

    public AbstractMember(String id) {
        super(id, true);
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
            tempClone.upgrade();
        }
    }

    public String getName() {
        return name + (upgradeCount > 0 ? " +" + upgradeCount : "");
    }

    public void endOfTurn(boolean isPlayer) {}

    public boolean isEvaded() {return false;}

    public void upgrade() {
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

    public String getKeyValue(String key) {
        int a;
        String s;
        switch (key) {
            case "A":
                a = calculatedAtk();
                s = a < baseAtk ? RED : a > baseAtk ? GREEN : BLUE;
                s += a + WHITE;
                return s;
            case "D":
                a = calculatedDef();
                s = a < baseDef ? RED : a > baseDef ? GREEN : BLUE;
                s += a + WHITE;
                return s;
            case "V":
                a = calculateValue();
                s = a < baseValue ? RED : a > baseValue ? GREEN : BLUE;
                s += a + WHITE;
                return s;
            case "X":
                a = calculateValue2();
                s = a < baseValue2 ? RED : a > baseValue2 ? GREEN : BLUE;
                s += a + WHITE;
                return s;
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

        float m = effect;

        for(OnIncreaseMemberDef g : WakTower.game.battle.turnMemberDef) {
            m *= g.multiplyMemberDef();
        }
        for(AbstractRelic r : WakTower.game.relics) {
            m *= r.multiplyDef();
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

    @Override
    public void die() {
        super.die();
        for(AbstractMember e : WakTower.game.battle.members) {
            if(e.isAlive()) return;
        }
        if(WakTower.game.battle.drawPile.size == 0 && WakTower.game.battle.discardPile.size == 0 && WakTower.game.battle.hand.size() == 0) {
            //TODO End Game
        }
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
