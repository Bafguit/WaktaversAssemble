package com.fastcat.assemble.handlers;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.abstracts.AbstractSkill;
import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.abstracts.AbstractEntity.EntityData;
import com.fastcat.assemble.abstracts.AbstractRelic.RelicData;
import com.fastcat.assemble.abstracts.AbstractSkill.SkillData;
import com.fastcat.assemble.abstracts.AbstractStatus.StatusData;
import com.fastcat.assemble.abstracts.AbstractSynergy.SynergyData;
import com.fastcat.assemble.utils.SpriteAnimation;
import com.fastcat.assemble.utils.SpriteAnimation.SpriteAnimationType;

import java.util.HashMap;

public class DataHandler {

    private static DataHandler instance;

    public final HashMap<String, AbstractUI.UIData> uiData = new HashMap<>();
    public final HashMap<String, AbstractEntity.EntityData> entityData = new HashMap<>();
    public final HashMap<String, AbstractRelic.RelicData> relicData = new HashMap<>();
    public final HashMap<String, AbstractMember.MemberData> memberData = new HashMap<>();
    public final HashMap<String, AbstractSynergy.SynergyData> synergyData = new HashMap<>();
    public final HashMap<String, AbstractStatus.StatusData> statusData = new HashMap<>();
    public final HashMap<String, AbstractSkill.SkillData> skillData = new HashMap<>();
    public final HashMap<String, SpriteAnimation> animation = new HashMap<>();

    public static DataHandler getInstance() {
        if(instance == null) instance = new DataHandler();
        return instance;
    }

    private DataHandler() {
        loadSync();
    }

    public void loadSync() {
        //ui data
        JsonValue json = FileHandler.getInstance().jsonMap.get("ui");
        for(JsonValue v : json.child) {
            uiData.put(v.name, new AbstractUI.UIData(v.name, v.child));
        }
    }

    public void loadAsync() {
        //animation
        for(SpriteAnimationType data : SpriteAnimationType.values()) {
            JsonValue json = FileHandler.getInstance().jsonMap.get(data.name());
            for(JsonValue v : json.child) {
                animation.put(v.name, new SpriteAnimation(v.name, data));
            }
        }
        animation.put("energy", new SpriteAnimation("energy"));

        //member data
        JsonValue json = FileHandler.getInstance().jsonMap.get("member");
        for(JsonValue v : json.child) {
            memberData.put(v.name, new AbstractMember.MemberData(v.name, v.child));
        }

        //skill data
        json = FileHandler.getInstance().jsonMap.get("skill");
        for(JsonValue v : json.child) {
            skillData.put(v.name, new SkillData(v.name, v.child));
        }

        //relic data
        json = FileHandler.getInstance().jsonMap.get("relic");
        for(JsonValue v : json.child) {
            relicData.put(v.name, new RelicData(v.name, v.child));
        }

        //synergy data
        json = FileHandler.getInstance().jsonMap.get("synergy");
        for(JsonValue v : json.child) {
            synergyData.put(v.name, new SynergyData(v.name, v.child));
        }

        //entity data
        json = FileHandler.getInstance().jsonMap.get("entity");
        for(JsonValue v : json.child) {
            entityData.put(v.name, new EntityData(v.name, v.child));
        }

        //status data
        json = FileHandler.getInstance().jsonMap.get("status");
        for(JsonValue v : json.child) {
            statusData.put(v.name, new StatusData(v.name, v.child));
        }
    }
}
