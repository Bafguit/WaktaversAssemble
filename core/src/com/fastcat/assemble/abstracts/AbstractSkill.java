package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FileHandler;

public abstract class AbstractSkill implements Cloneable {

    public final SkillData data;
    public Sprite img;

    public AbstractSkill(String id) {
        data = DataHandler.getInstance().skillData.get(id);
    }

    public boolean canUse() {
        return true;
    }

    public final void use() {

    }

    public AbstractSkill cpy() {
        AbstractSkill m;
        try {
            m = (AbstractSkill) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        m.img = new Sprite(data.img);
        return m;
    }

    public static class SkillData {
        public final String id, name, desc, flavor;
        public final Texture img;

        public SkillData(String id, JsonValue json) {
            this.id = id;
            name = json.getString("name");
            desc = json.getString("desc");
            flavor = json.getString("flavor");
            img = FileHandler.getTexture("skill/" + id);
        }
    }
}
