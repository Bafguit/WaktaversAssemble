package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.utils.DamageInfo;

public abstract class AbstractRelic {

    public final RelicData data;
    public String id, name, desc, flavor;
    public int counter = -1;
    public TextureRegionDrawable img;
    public boolean isDone = false;
    public RelicRarity rarity;

    public AbstractRelic(String id) {
        this.id = id;
        data = DataHandler.getInstance().relicData.get(id);
        name = data.name;
        desc = data.desc;
        flavor = data.flavor;
        img = new TextureRegionDrawable(data.img);
        rarity = data.rarity;
    }

    public int damageTake(DamageInfo info) {
        return info.damage;
    }

    public float damageTakeMultiply(DamageInfo info) {
        return 1f;
    }

    public void damageTaken(DamageInfo info) {}

    public void onAttack(DamageInfo info, Array<AbstractEntity> target) {}

    public void onDamage(DamageInfo info, AbstractEntity target) {}

    public int calculateAtk(int atk) {
        return atk;
    }

    public int calculateDef(int def) {
        return def;
    }

    public float multiplyAtk() {
        return 1f;
    }

    public float multiplyDef() {
        return 1f;
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
    
    public void onGain() {}

    public void onLose() {}

    public void endOfTurn(boolean isPlayer) {}

    public void endOfRound() {}

    public void startOfTurn(boolean isPlayer) {}

    public void startOfBattle() {}

    public void onSummon(AbstractMember m) {}

    public int repeatAmount(AbstractMember m) {
        return 1;
    }

    public static class RelicData {
        public final String id, name, desc, flavor;
        public final Texture img;
        public final RelicRarity rarity;

        public RelicData(String id, JsonValue json) {
            this.id = id;
            name = json.getString("name");
            desc = json.getString("desc");
            flavor = json.getString("flavor");
            img = FileHandler.getPng("relic/" + id);
            rarity = RelicRarity.valueOf(json.getString("rarity"));
        }
    }

    public enum RelicRarity {
        common, rare, legeno
    }
}
