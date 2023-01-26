package com.fastcat.assemble.strings;

import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.handlers.FileHandler;

import java.util.HashMap;

public class SkillString {

    private final HashMap<String, SkillData> data = new HashMap<>();

    public SkillString() {
        generateString(FileHandler.jsonMap.get(FileHandler.JsonType.CHAR));
    }

    private void generateString(JsonValue json) {
        for (JsonValue js : json) {
            String id = js.name;
            if (!id.equals("")) {
                SkillData data = new SkillData();
                data.NAME = js.get("NAME").asString();
                JsonValue temp = js.get("DESC");
                if (temp != null) {
                    data.DESC = temp.asString();
                }
                temp = js.get("KEY");
                if (temp != null) {
                    data.KEY = temp.asStringArray();
                }
                this.data.put(id, data);
            }
        }
    }

    public SkillData get(String id) {
        return data.get(id);
    }

    public static class SkillData {
        public String NAME;
        public String DESC = "";
        public String[] KEY;
    }
}
