package com.fastcat.assemble.handlers;

import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.abstracts.AbstractRoom;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.abstracts.AbstractEntity.EntityData;
import com.fastcat.assemble.abstracts.AbstractRelic.RelicData;
import com.fastcat.assemble.abstracts.AbstractRoom.RoomData;
import com.fastcat.assemble.abstracts.AbstractSynergy.SynergyData;
import com.fastcat.assemble.uis.SpriteAnimation;
import com.fastcat.assemble.uis.SpriteAnimation.SpriteAnimationType;

import java.util.HashMap;

public class DataHandler {

    private static DataHandler instance;

    public static String LOADING = "리소스 불러오는 중...";
    public static String TURN_END = "턴 종료";
    public static String GAME_START = "게임 시작";
    public static String LOAD_GAME = "불러오기";
    public static String DICTIONARY = "도감";
    public static String SETTING = "설정";
    public static String MEMBER_TURN = "나의 턴";
    public static String ENEMY_TURN = "적 턴";
    public static String EVADE = "회피";

    public final HashMap<String, AbstractUI.UIData> uiData = new HashMap<>();
    public final HashMap<String, AbstractEntity.EntityData> enemyData = new HashMap<>();
    public final HashMap<String, AbstractRelic.RelicData> relicData = new HashMap<>();
    public final HashMap<String, AbstractEntity.EntityData> memberData = new HashMap<>();
    public final HashMap<String, AbstractSynergy.SynergyData> synergyData = new HashMap<>();
    public final HashMap<String, AbstractRoom.RoomData> roomData = new HashMap<>();
    public final HashMap<String, SpriteAnimation> animation = new HashMap<>();

    public static DataHandler getInstance() {
        if(instance == null) instance = new DataHandler();
        return instance;
    }

    private DataHandler() {
        loadSync();
        setDefaultLanguage();
    }

    private void setDefaultLanguage() {
        LOADING = uiData.get("loading").text[0];
        TURN_END = uiData.get("turnEnd").text[0];
        GAME_START = uiData.get("gameStart").text[0];
        LOAD_GAME = uiData.get("loadGame").text[0];
        DICTIONARY = uiData.get("dictionary").text[0];
        SETTING = uiData.get("setting").text[0];
        MEMBER_TURN = uiData.get("turnChangeEffect").text[0];
        ENEMY_TURN = uiData.get("turnChangeEffect").text[1];
        EVADE = uiData.get("evade").text[0];
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
            memberData.put(v.name, new AbstractEntity.EntityData(v.name, SpriteAnimationType.member, v));
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
            enemyData.put(v.name, new EntityData(v.name, SpriteAnimationType.entity, v));
        }

        //entity data
        json = FileHandler.getInstance().jsonMap.get("room");
        for(JsonValue v : json) {
            for(int i = 0; i < v.size; i++) {
                JsonValue v2 = v.get(i);
                roomData.put(v.name + "_" + v2.name, new RoomData(i, v2.asStringArray()));
            }
        }
    }
}
