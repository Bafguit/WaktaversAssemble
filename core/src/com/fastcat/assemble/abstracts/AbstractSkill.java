package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FileHandler;

public abstract class AbstractSkill implements Cloneable {

    public final SkillData data;
    public Sprite img;
    public int upgradeCount = 0;
    public String name, desc;

    public AbstractSkill(String id) {
        data = DataHandler.getInstance().skillData.get(id);
        name = data.name;
        desc = data.desc;
        img = new Sprite(data.img);
    }

    public boolean canUse() {
        return true;
    }

    public final void use() {

    }

    public boolean canUpgrade() {
        return upgradeCount < 1;
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
        public final Sprite img;

        public SkillData(String id, JsonValue json) {
            this.id = id;
            name = json.getString("name");
            desc = json.getString("desc");
            flavor = json.getString("flavor");
            img = FileHandler.getSkill(id);
        }
    }
}
