package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.utils.DamageInfo;

public abstract class AbstractStatus {

    private final StatusData data;

    public final String id;
    public String name, desc;
    public int amount;
    public TextureRegionDrawable img;
    public AbstractEntity owner;
    public boolean hasAmount, canGoNegative;

    public AbstractStatus(String id) {
        this.id = id;
        data = DataHandler.getInstance().statusData.get(id);
        name = data.name;
        desc = data.desc;
        img = new TextureRegionDrawable(data.img);
    }

    public String getDesc() {
        return desc;
    }

    public void apply(int amount) {}

    public void onRemove() {}

    public void onInitial() {}

    public void startOfTurn(boolean isPlayer) {}

    public void endOfTurn(boolean isPlayer) {}

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

    public void onSummon(AbstractMember m) {}

    public static class StatusData {
        public final String id, name, desc;
        public final String[] exDesc;
        public final Texture img;

        public StatusData(String id, JsonValue json) {
            this.id = id;
            name = json.getString("name");
            desc = json.getString("desc");
            exDesc = json.get("exDesc").asStringArray();
            img = FileHandler.getTexture("status/" + id);
        }
    }
}
