package com.fastcat.assemble.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.handlers.ResourceHandler.MultipleResourceRequest;
import lombok.Getter;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class FileHandler {

    //Instance
    private static FileHandler instance;

    //Maps
    private static final Array<HashMap> maps = new Array<>();

    public static final HashMap<JsonType, JsonValue> jsonMap = new HashMap<>();

    public static final HashMap<String, Sprite> bg = new HashMap<>();

    public static final HashMap<String, Sprite> ui = new HashMap<>();

    public static final HashMap<String, Sprite> character = new HashMap<>();

    public static final HashMap<String, Sprite> card = new HashMap<>();

    public static final HashMap<String, Sprite> dice = new HashMap<>();

    public static final HashMap<String, Sprite> skill = new HashMap<>();

    public static final HashMap<String, Sprite> status = new HashMap<>();

    public static final HashMap<String, TextureAtlas> diceAtlas = new HashMap<>();

    public static FileHandler getInstance() {
        if (instance == null) return (instance = new FileHandler());
        return instance;
    }

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
        generateDiceAtlas(resourceHandler);
        generateUI(resourceHandler);
        generateBG(resourceHandler);
        generateChar(resourceHandler);
        generateCard(resourceHandler);
        generateSkill(resourceHandler);
        generateStatus(resourceHandler);
    }

    private static void generateHashMap() {
        maps.add(dice);
        maps.add(character);
        maps.add(skill);
        maps.add(status);
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
        jsonMap.clear();

        jsonMap.put(JsonType.DICE, generateJson("json/dice.json"));
        jsonMap.put(JsonType.CHAR, generateJson("json/character.json"));
        jsonMap.put(JsonType.SKILL, generateJson("json/skill.json"));
        jsonMap.put(JsonType.STATUS, generateJson("json/status.json"));
        //StringHandler.generate();
    }

    private void generateDice(ResourceHandler resourceHandler) {
        dice.clear();

        for (JsonValue js : jsonMap.get(JsonType.DICE)) {

            ResourceHandler.ResourceRequest<Texture> request = new ResourceHandler.ResourceRequest<>(
                    "image/dice/" + js.name + ".png",
                    Texture.class,
                    (resource, args) -> {
                        String name = args[0].toString();
                        dice.put(name, new Sprite((Texture) resource));
                    },
                    js.name);

            resourceHandler.requestResource(request);
        }
    }

    private void generateDiceAtlas(ResourceHandler resourceHandler) {
        diceAtlas.clear();

        HashMap<String, String> resources = new HashMap<>();
        resources.put("Dice", "atlas/dice/Dice/Dice.atlas");
        resources.put("Fraud3", "atlas/dice/Fraud3/Fraud3.atlas");

        resourceHandler.requestResource(new MultipleResourceRequest<>(resources, TextureAtlas.class, (resource, args) -> {
            TextureAtlas texture = (TextureAtlas) resource;
            for(Texture t : texture.getTextures()) {
                t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            }
            String resourceName = args[0].toString();

            diceAtlas.put(resourceName, texture);
        }));
    }

    private void generateBG(ResourceHandler resourceHandler) {
        bg.clear();

        HashMap<String, String> resources = new HashMap<>();
        resources.put("GRID", "image/bg/grid.png");

        resourceHandler.requestResource(new MultipleResourceRequest<>(resources, Texture.class, (resource, args) -> {
            Texture texture = (Texture) resource;
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            String resourceName = args[0].toString();

            bg.put(resourceName, new Sprite(texture));
        }));
    }

    private void generateUI(ResourceHandler resourceHandler) {
        ui.clear();

        HashMap<String, String> resources = new HashMap<>();
        resources.put("TEMP", "image/ui/temp.png");
        resources.put("TILE", "image/ui/tile.png");
        resources.put("BACK_32", "image/ui/background32.png");
        resources.put("DIR", "image/ui/direction.png");
        resources.put("SUB_TOP", "image/ui/sub_t.png");
        resources.put("SUB_MID", "image/ui/sub_m.png");
        resources.put("SUB_BOT", "image/ui/sub_b.png");

        resourceHandler.requestResource(new MultipleResourceRequest<>(resources, Texture.class, (resource, args) -> {
            Texture texture = (Texture) resource;
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            String resourceName = args[0].toString();

            ui.put(resourceName, new Sprite(texture));
        }));
    }

    private void generateChar(ResourceHandler resourceHandler) {
        character.clear();

        HashMap<String, String> resources = new HashMap<>();
        resources.put("Test", "image/char/Test.png");
        resources.put("Mousse", "image/char/Mousse.png");

        resourceHandler.requestResource(new MultipleResourceRequest<>(resources, Texture.class, (resource, args) -> {
            String resourceName = args[0].toString();

            character.put(resourceName, new Sprite((Texture) resource));
        }));
    }

    private void generateSkill(ResourceHandler resourceHandler) {
        skill.clear();

        HashMap<String, String> resources = new HashMap<>();
        for(JsonValue js : jsonMap.get(JsonType.SKILL)) {
            resources.put(js.name, "image/skill/" + js.name + ".png");

            resourceHandler.requestResource(new MultipleResourceRequest<>(resources, Texture.class, (resource, args) -> {
                String resourceName = args[0].toString();
                skill.put(resourceName, new Sprite((Texture) resource));
            }));
        }
    }

    private void generateStatus(ResourceHandler resourceHandler) {
        status.clear();

        HashMap<String, String> resources = new HashMap<>();
        for(JsonValue js : jsonMap.get(JsonType.STATUS)) {
            resources.put(js.name, "image/status/" + js.name + ".png");

            resourceHandler.requestResource(new MultipleResourceRequest<>(resources, Texture.class, (resource, args) -> {
                String resourceName = args[0].toString();
                status.put(resourceName, new Sprite((Texture) resource));
            }));
        }
    }

    private void generateCard(ResourceHandler resourceHandler) {
        card.clear();

        HashMap<String, String> resources = new HashMap<>();
        resources.put("Test", "image/card/Test.png");

        resourceHandler.requestResource(new MultipleResourceRequest<>(resources, Texture.class, (resource, args) -> {
            Texture texture = (Texture) resource;
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            String resourceName = args[0].toString();

            card.put(resourceName, new Sprite(texture));
        }));
    }

    public enum JsonType {
        DICE,
        CHAR,
        SKILL,
        STATUS
    }
}
