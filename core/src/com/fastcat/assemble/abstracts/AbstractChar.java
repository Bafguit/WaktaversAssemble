package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.actions.SynergyFlashAction;
import com.fastcat.assemble.handlers.ActionHandler;


public class AbstractChar {

    public final CharData data;

    public String id;
    public String name;
    public String desc;
    public String flavor;
    public Sprite img;
    public final AbstractSynergy[] synergy;

    public AbstractChar(String id) {
        this.id = id;
        data = new CharData(id);
        name = data.name;
        desc = data.desc;
        flavor = data.flavor;
        img = new Sprite(data.img);
        synergy = new AbstractSynergy[data.synergy.length];
    }

    public static class CharData {
        public final String id;
        public final String name;
        public final String desc;
        public final String flavor;
        public final Texture img;
        public final String[] synergy;

        public CharData(String id, JsonValue json) {
            this.id = id;
            name = json.getString("name");
            desc = json.getString("desc");
            flavor = json.getString("flavor");
            img = FileHandler.getTexture("member/" + id);
            synergy = json.getStringArray("synergy");
        }
    }
}
