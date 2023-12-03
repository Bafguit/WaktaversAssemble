package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.SpriteAnimation;


public class AbstractMember {

    public final MemberData data;

    public String id;
    public String name;
    public String desc;
    public String flavor;
    public Sprite img;
    public int upgradeCount = 0;
    public int upgradeLimit = 1;
    public SpriteAnimation animation;
    public final AbstractSynergy[] synergy;

    public AbstractMember(String id) {
        this.id = id;
        data = DataHandler.getInstance().memberData.get(id);
        name = data.name;
        desc = data.desc;
        flavor = data.flavor;
        img = new Sprite(data.img);
        animation = data.animation.cpy();
        synergy = new AbstractSynergy[data.synergy.length];
    }

    public String getName() {
        return name + (upgradeCount > 0 ? " +" + upgradeCount : "");
    }

    public void startOfTurn(boolean isPlayer) {}

    public int damageTake(DamageInfo info, boolean isPlayer) {
        return info.damage;
    }

    public float damageTakeMultiply(DamageInfo info, boolean isPlayer) {
        return 1f;
    }

    public void damageTaken(DamageInfo info, boolean isPlayer) {}

    public static class MemberData {
        public final String id;
        public final String name;
        public final String desc;
        public final String flavor;
        public final Texture img;
        public final SpriteAnimation animation;
        public final String[] synergy;

        public MemberData(String id, JsonValue json) {
            this.id = id;
            name = json.getString("name");
            desc = json.getString("desc");
            flavor = json.getString("flavor");
            img = FileHandler.getTexture("member/" + id);
            animation = DataHandler.getInstance().animation.get(id);
            synergy = json.get("synergy").asStringArray();
        }
    }
}
