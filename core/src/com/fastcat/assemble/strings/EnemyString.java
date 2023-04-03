package com.fastcat.assemble.strings;

import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.handlers.FileHandler;

import java.util.HashMap;

public class EnemyString {

    private final HashMap<String, EnemyData> data = new HashMap<>();

    public EnemyString() {
        generateString(FileHandler.jsonMap.get(FileHandler.JsonType.ENEMY));
    }

    private void generateString(JsonValue json) {
        for (JsonValue js : json) {
            String id = js.name;
            if (!id.equals("")) {
                EnemyData data = new EnemyData();
                data.NAME = js.get("NAME").asString();
                data.DESC = js.get("DESC").asString();
                this.data.put(id, data);
            }
        }
    }

    public EnemyData get(String id) {
        return data.get(id);
    }

    public static class EnemyData {
        public String NAME;
        public String DESC = "";
    }
}
