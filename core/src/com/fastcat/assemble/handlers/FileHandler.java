package com.fastcat.assemble.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.uis.SpriteAnimation;
import com.fastcat.assemble.utils.SkinAtlasLoader;
import com.fastcat.assemble.utils.TextureAtlasFilterLoader;
import com.fastcat.assemble.utils.WebpPixmapLoader;
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
        assetManager.setLoader(Pixmap.class, ".webp", new WebpPixmapLoader(resolver));
        assetManager.setLoader(TextureAtlas.class, new TextureAtlasFilterLoader(resolver));
        assetManager.setLoader(Skin.class, new SkinAtlasLoader(resolver));
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
        generateMember();
        //todo 애니메이션과 ui를 제외한 다른 이미지는 전부 TextureAtlas로 불러오기
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
        jsonMap.put("status", generateJson("json/" + SettingHandler.setting.language + "/status.json"));
        jsonMap.put("synergy", generateJson("json/" + SettingHandler.setting.language + "/synergy.json"));
        jsonMap.put("ui", generateJson("json/" + SettingHandler.setting.language + "/ui.json"));
    }

    private void generateUI() {
        assetManager.load("atlas/ui.atlas", Skin.class);
        assetManager.load("image/ui/tile.webp", Pixmap.class);
        assetManager.load("image/ui/cardBg.webp", Pixmap.class);
        assetManager.load("image/ui/cardDesc.webp", Pixmap.class);
        assetManager.load("image/ui/cardFrame.webp", Pixmap.class);
        assetManager.load("image/ui/memberTile.webp", Pixmap.class);
        assetManager.load("image/ui/synergyIcon.webp", Pixmap.class);
        assetManager.load("image/ui/sub_top.png", Pixmap.class);
        assetManager.load("image/ui/sub_mid.png", Pixmap.class);
        assetManager.load("image/ui/sub_bot.png", Pixmap.class);
        assetManager.load("image/ui/hb_left.webp", Pixmap.class);
        assetManager.load("image/ui/hb_mid.webp", Pixmap.class);
        assetManager.load("image/ui/hb_right.webp", Pixmap.class);
        assetManager.load("image/ui/hb_line.webp", Pixmap.class);
        assetManager.load("image/ui/hb_yet_left.webp", Pixmap.class);
        assetManager.load("image/ui/hb_yet_mid.webp", Pixmap.class);
        assetManager.load("image/ui/hb_yet_right.webp", Pixmap.class);
        assetManager.load("image/ui/synergyNameBg.png", Pixmap.class);
        assetManager.load("image/ui/topbar.png", Pixmap.class);
        assetManager.load("image/ui/checkbox_on.png", Pixmap.class);
        assetManager.load("image/ui/checkbox_off.png", Pixmap.class);
        assetManager.load("image/ui/button.png", Pixmap.class);

        //BG
        assetManager.load("image/bg/way_lab.png", Pixmap.class);
        assetManager.load("image/bg/half.webp", Pixmap.class);
        assetManager.load("image/bg/cat.webp", Pixmap.class);
    }

    private void generateMember() {
        for(JsonValue v : jsonMap.get("relic")) {
            assetManager.load("image/relic/" + v.name + ".png", Pixmap.class);
        }

        for(JsonValue v : jsonMap.get("status")) {
            assetManager.load("image/status/" + v.name + ".webp", Pixmap.class);
        }

        //assetManager.load("image/member/member.atlas", TextureAtlas.class);
        assetManager.load("image/member/member.atlas", TextureAtlas.class);
        assetManager.load("image/synergy/synergy.atlas", Skin.class);
        //assetManager.load("atlas/relic.atlas", TextureAtlas.class);
        //assetManager.load("atlas/status.atlas", TextureAtlas.class);
    }

    private void generateAnimationSprites() {
        for(SpriteAnimation.SpriteAnimationType type : SpriteAnimation.SpriteAnimationType.values()) {
            for(JsonValue v : jsonMap.get(type.name())) {
                String url = "animation/" + type.name() + "/" + v.name;
                JsonValue v2 = generateJson(url + "/config.json");
                jsonMap.put(url, v2);
                for(JsonValue v3 : v2) {
                    assetManager.load(url + "/" + v3.name + ".atlas", TextureAtlas.class);
                }
            }
        }

        //ui animation data
        JsonValue v2 = generateJson("animation/ui/energy/config.json");
        jsonMap.put("animation_ui_energy", v2);
        for(JsonValue v3 : v2) {
            assetManager.load("animation/ui/energy/" + v3.name + ".atlas", TextureAtlas.class);
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
        Texture t = new Texture(instance.assetManager.get("image/" + path + ".webp", Pixmap.class));
		t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return t;
    }

    public static Texture getPng(String path) {
        Texture t = new Texture(instance.assetManager.get("image/" + path + ".png", Pixmap.class));
		t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return t;
    }

    public static Sprite getRelic(String id) {
        TextureAtlas atlas = instance.assetManager.get("atlas/relic.atlas", TextureAtlas.class);
        return atlas.createSprite(id);
    }

    public static TextureRegionDrawable getMember(String id) {
        TextureAtlas atlas = instance.assetManager.get("image/member/member.atlas", TextureAtlas.class);
        return new TextureRegionDrawable(atlas.findRegion(id));
    }

    public static Sprite getStatus(String id) {
        TextureAtlas atlas = instance.assetManager.get("atlas/status.atlas", TextureAtlas.class);
        return atlas.createSprite(id);
    }

    public static Sprite getSynergy(String id) {
        Skin atlas = instance.assetManager.get("image/synergy/synergy.atlas", Skin.class);
        Sprite s = atlas.getSprite(id);
        s.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return s;
    }

    public static Skin getSynergy() {
        return instance.assetManager.get("image/synergy/synergy.atlas", Skin.class);
    }

    public static TextureAtlas getMember() {
        return instance.assetManager.get("image/member/member.atlas", TextureAtlas.class);
    }

    public static Skin getUI() {
        return instance.assetManager.get("atlas/ui.atlas", Skin.class);
    }

    public static TextureAtlas getAtlas(String url) {
        return instance.assetManager.get(url + ".atlas", TextureAtlas.class);
    }
}
