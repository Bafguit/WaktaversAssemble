package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.utils.DamageInfo;

public abstract class AbstractStatus {

    private final StatusData data;

    public final String id;
    public String name, desc;
    public int amount;
    public boolean hasAmount, canGoNegative;

    public AbstractStatus(String id) {
        this.id = id;
        data = DataHandler.getInstance().statusData.get(id);
        name = data.name;
        desc = data.desc;
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
