package com.fastcat.assemble.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.utils.SpriteAnimation;
import com.fastcat.assemble.utils.WebpTextureLoader;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class FileHandler {

    //Instance
    private static FileHandler instance;

    public final HashMap<String, JsonValue> jsonMap;
    public final AssetManager assetManager;
    private final FileHandleResolver resolver;

    public static FileHandler getInstance() {
        if (instance == null) return (instance = new FileHandler());
        return instance;
    }

    private FileHandler() {
        assetManager = new AssetManager();
        resolver = new InternalFileHandleResolver();
        assetManager.setLoader(Texture.class, ".webp", new WebpTextureLoader(resolver));
        jsonMap = new HashMap<>();
        loadSync();
        loadAsync();
    }

    private void loadSync() {
        loadJson();
        //todo 로딩창
    }

    private void loadAsync() {
        generateUI();
        generateAnimationSprites();
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
        jsonMap.put("entity", generateJson("json/" + SettingHandler.setting.language + "/entity.json"));
        jsonMap.put("relic", generateJson("json/" + SettingHandler.setting.language + "/relic.json"));
        jsonMap.put("member", generateJson("json/" + SettingHandler.setting.language + "/member.json"));
        jsonMap.put("skill", generateJson("json/" + SettingHandler.setting.language + "/skill.json"));
        jsonMap.put("status", generateJson("json/" + SettingHandler.setting.language + "/status.json"));
        jsonMap.put("synergy", generateJson("json/" + SettingHandler.setting.language + "/synergy.json"));
        jsonMap.put("ui", generateJson("json/" + SettingHandler.setting.language + "/ui.json"));
    }

    private void generateUI() {
        assetManager.load("image/ui/tile.webp", Texture.class);
        assetManager.load("image/ui/cardBg.webp", Texture.class);
    }

    private void generateAnimationSprites() {
        for(SpriteAnimation.SpriteAnimationType type : SpriteAnimation.SpriteAnimationType.values()) {
            for(JsonValue v : jsonMap.get(type.name())) {
                JsonValue v2 = generateJson("animation/" + type.name() + "/" + v.name + "/config.json");
                jsonMap.put("animation_" + type.name() + "_" + v.name, v2);
                for(JsonValue v3 : v2) {
                    for(int i = 0; i < v3.getInt("frameCount"); i++) {
                        assetManager.load("animation/" + type.name() + "/" + v.name + "/" + v3.name + "/" + i + ".webp", Texture.class);
                    }
                }
            }
        }

        //ui animation data
        JsonValue v2 = generateJson("animation/ui/energy/config.json");
        jsonMap.put("animation_ui_energy", v2);
        for(JsonValue v3 : v2) {
            for(int i = 0; i < v3.getInt("frameCount"); i++) {
                assetManager.load("animation/ui/energy/" + v3.name + "/" + i + ".webp", Texture.class);
            }
        }
    }

    public static float getProcess() {
        return instance.assetManager.getProgress();
    }

    public static boolean isFinished() {
        return instance.assetManager.isFinished();
    }

    public static void loadAsset() {
        instance.assetManager.update();
    }

    public static Texture getTexture(String path) {
        return instance.assetManager.get("image/" + path + ".webp", Texture.class);
    }
}
