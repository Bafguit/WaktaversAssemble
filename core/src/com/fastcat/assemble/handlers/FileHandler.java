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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.utils.JsonLoader;
import com.fastcat.assemble.utils.WebpPixmapLoader;
import com.fastcat.assemble.utils.WebpTextureLoader;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class FileHandler {

    //Instance
    private static FileHandler instance;

    public final AssetManager assetManager;
    private final FileHandleResolver resolver;

    public static FileHandler getInstance() {
        if (instance == null) return (instance = new FileHandler());
        return instance;
    }

    private FileHandler() {
        assetManager = new AssetManager();
        resolver = new InternalFileHandleResolver();
        loadSync();
        loadAsync();
    }

    private void loadSync() {
        loadJson();
        //todo 로딩창
    }

    private void loadAsync() {
        generateUI();
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
        assetManager.setLoader(JsonValue.class, new JsonLoader(resolver));
        assetManager.load("json/character.json", JsonValue.class);
    }

    private void generateUI() {
        assetManager.setLoader(Texture.class, ".webp", new WebpTextureLoader(resolver));
        assetManager.load("image/ui/tile.webp", Texture.class);
    }

    public static float geProcess() {
        return instance.assetManager.getProgress();
    }

    public static boolean isFinished() {
        return instance.assetManager.isFinished();
    }

    public static void loadAsset() {
        instance.assetManager.update();
    }
}
