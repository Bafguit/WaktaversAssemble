package com.fastcat.assemble.strings;

import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.handlers.FileHandler;

import java.util.HashMap;

public class StatusString {

    private final HashMap<String, StatusData> data = new HashMap<>();

    public StatusString() {
        generateString(FileHandler.jsonMap.get(FileHandler.JsonType.STATUS));
    }

    private void generateString(JsonValue json) {
        for (JsonValue js : json) {
            String id = js.name;
            if (!id.equals("")) {
                StatusData data = new StatusData();
                data.NAME = js.get("NAME").asString();
                data.DESC = js.get("DESC").asString();
                JsonValue ex = js.get("EX_DESC");
                if(ex != null) {
                    data.EX_DESC = ex.asStringArray();
                }
                this.data.put(id, data);
            }
        }
    }

    public StatusData get(String id) {
        return data.get(id);
    }

    public static class StatusData {
        public String NAME;
        public String DESC = "";
        public String[] EX_DESC;
    }
}
