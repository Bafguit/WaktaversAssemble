package com.fastcat.assemble.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.handlers.ResourceHandler.MultipleResourceRequest;
import lombok.Getter;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class FileHandler {

    // 관리
    private static final Array<HashMap> maps = new Array<>();

    @Getter
    private static final HashMap<JsonType, JsonValue> jsonMap = new HashMap<>();

    @Getter
    private static final HashMap<String, Sprite> bg = new HashMap<>();

    @Getter
    private static final HashMap<String, Sprite> ui = new HashMap<>();

    @Getter
    private static final HashMap<String, Sprite> dice = new HashMap<>();

    /***
     * Load files which do not require being called on main thread
     */
    public void loadFiles() {
        loadJson();
        generateHashMap();
    }

    /***
     * Load resources which must be loaded on main thread or asset manager
     */
    public void loadResources(ResourceHandler resourceHandler) {
        generateDice(resourceHandler);
    }

    private static void generateHashMap() {
        maps.add(dice);
    }

    @SuppressWarnings("NewApi")
    private static JsonValue generateJson(String url) {
        JsonReader jsonReader = new JsonReader();
        FileHandle fileHandle = Gdx.files.internal(url);
        InputStreamReader is = new InputStreamReader(fileHandle.read(), StandardCharsets.UTF_8);
        return jsonReader.parse(is);
    }

    @SuppressWarnings("NewApi")
    public static JsonValue generateJson(FileHandle fileHandle) {
        JsonReader jsonReader = new JsonReader();
        InputStreamReader is = new InputStreamReader(fileHandle.read(), StandardCharsets.UTF_8);
        return jsonReader.parse(is);
    }

    private void loadJson() {
        jsonMap.put(JsonType.DICE, generateJson("json/dice.json"));
        //StringHandler.generate();
    }

    private void generateDice(ResourceHandler resourceHandler) {
        dice.clear();

        for (JsonValue js : jsonMap.get(JsonType.DICE)) {

            ResourceHandler.ResourceRequest<Texture> request = new ResourceHandler.ResourceRequest<>(
                    "image/dice/" + js.name + ".png",
                    Texture.class,
                    (resource, args) -> {
                        Texture texture = (Texture) resource;
                        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                        String name = args[0].toString();
                        dice.put(name, new Sprite(texture));
                    },
                    js.name);

            resourceHandler.requestResource(request);
        }
    }

    public enum JsonType {
        DICE
    }
}
