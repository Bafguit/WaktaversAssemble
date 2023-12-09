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
        for(JsonValue v : json) {
            uiData.put(v.name, new AbstractUI.UIData(v.name, v.asStringArray()));
        }
    }

    public void loadAsync() {
        //animation
        for(SpriteAnimationType data : SpriteAnimationType.values()) {
            JsonValue json = FileHandler.getInstance().jsonMap.get(data.name());
            for(JsonValue v : json) {
                animation.put(v.name, new SpriteAnimation(v.name, data));
            }
        }
        animation.put("energy", new SpriteAnimation("energy"));

        //member data
        JsonValue json = FileHandler.getInstance().jsonMap.get("member");
        for(JsonValue v : json) {
            memberData.put(v.name, new AbstractMember.MemberData(v.name, v));
        }

        //skill data
        json = FileHandler.getInstance().jsonMap.get("skill");
        for(JsonValue v : json) {
            skillData.put(v.name, new SkillData(v.name, v));
        }

        //relic data
        json = FileHandler.getInstance().jsonMap.get("relic");
        for(JsonValue v : json) {
            relicData.put(v.name, new RelicData(v.name, v));
        }

        //synergy data
        json = FileHandler.getInstance().jsonMap.get("synergy");
        for(JsonValue v : json) {
            synergyData.put(v.name, new SynergyData(v.name, v));
        }

        //entity data
        json = FileHandler.getInstance().jsonMap.get("entity");
        for(JsonValue v : json) {
            entityData.put(v.name, new EntityData(v.name, v));
        }

        //status data
        json = FileHandler.getInstance().jsonMap.get("status");
        for(JsonValue v : json) {
            statusData.put(v.name, new StatusData(v.name, v));
        }
    }
}
