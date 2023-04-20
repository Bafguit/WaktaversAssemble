package com.fastcat.assemble.handlers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.interfaces.OnMouseScrolled;

import java.nio.ByteBuffer;
import java.util.zip.Deflater;

public final class InputHandler {

    public static boolean isLeftClick;
    public static boolean isLeftClicking;
    public static boolean isRightClick;
    public static boolean isRightClicking;
    public static boolean isCursorInScreen;
    public static boolean isDesktop;

    public static boolean cancel;
    public static boolean map;
    public static boolean info;
    public static boolean sc;

    public static float scaleA;
    public static float scaleX;
    public static float scaleY;
    public static float scrollH;
    public static int mx;
    public static int my;

    public static Array<OnMouseScrolled> scrollListener = new Array<>();

    public static boolean textInputMode;
    private static String typedText = "";
    private static int backspaces = 0;
    /***
     * Instance of handler.
     * Initialized on getInstance()
     */
    private static InputHandler instance;

    private InputHandler() {
        isLeftClick = false;
        isLeftClicking = false;
        isDesktop = Gdx.app.getType() == Application.ApplicationType.Desktop;
        mx = 0;
        my = 0;
        int w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
        scaleX = (float) w / 1920.0f;
        scaleY = (float) h / 1080.0f;
        float a = ((float) h) / ((float) w);

        if(a <= 0.5625f) {
            scaleA = scaleY;
        } else {
            scaleA = scaleX;
        }

        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyTyped(char c) {
                if (textInputMode) {
                    if (c == '\b') {
                        if (typedText.length() == 0) backspaces++;
                        else typedText = typedText.substring(0, typedText.length() - 1);
                    } else if ((c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
                        typedText += c;
                    } else if (c >= 'a' && c <= 'z') {
                        typedText += (char) (c - 32);
                    }
                    return true;
                }
                return false;
            }

            @Override
            public boolean scrolled (float amountX, float amountY) {
                if(amountY != 0) {
                    for(OnMouseScrolled s : scrollListener) {
                        s.scrolled(amountY);
                    }
                    return true;
                }
                return false;
            }
        });

        Gdx.input.setCatchKey(Keys.BACK, true);
    }

    /***
     * Returns instance of handler, if not exist, create new.
     * @return instance of handler
     */
    public static InputHandler getInstance() {
        if (instance == null) return (instance = new InputHandler());
        return instance;
    }

    public static void setTextInputMode(boolean textInputMode) {
        InputHandler.textInputMode = textInputMode;
    }

    public static String getTypedText(String formerText) {
        StringBuilder sb = new StringBuilder();
        sb.append(formerText);
        sb.setLength(Math.max(formerText.length() - backspaces, 0));
        sb.append(typedText);
        typedText = "";
        backspaces = 0;
        return sb.toString();
    }

    public void update() {
        int gx = Gdx.input.getX(), gy = Gdx.input.getY(), sw = Gdx.graphics.getWidth(), sh = Gdx.graphics.getHeight();
        float sx = (float) sw / 1920.0f, sy = (float) sh / 1080.0f;
        if(sx > 0) scaleX = sx;
        if(sy > 0) scaleY = sy;
        float a = ((float) sh) / ((float) sw);
        if(a <= 0.5625f) {
            scaleA = scaleY;
        } else {
            scaleA = scaleX;
        }
        isCursorInScreen = gx < sw && gx > 0 && gy < sh && gy > 0;

        mx = Math.max(Math.min(gx, sw), 0);
        my = sh - Math.max(Math.min(gy, sh), 0);
        cancel = Gdx.input.isKeyJustPressed(Buttons.BACK) || Gdx.input.isKeyJustPressed(Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Keys.BACK);

        if (isDesktop) {
            isLeftClick = Gdx.input.isButtonJustPressed(Buttons.LEFT);
            isLeftClicking = Gdx.input.isButtonPressed(Buttons.LEFT);
            isRightClick = Gdx.input.isButtonJustPressed(Buttons.RIGHT);
            isRightClicking = Gdx.input.isButtonPressed(Buttons.RIGHT);
        } else {
            isLeftClick = Gdx.input.justTouched();
            isLeftClicking = Gdx.input.isTouched();
            isRightClick = cancel;
            isRightClicking = cancel;
        }

        if (textInputMode && (isLeftClick || Gdx.input.isKeyJustPressed(Keys.ENTER) || cancel)) {
            textInputMode = false;
            if(!isDesktop) Gdx.input.setOnscreenKeyboardVisible(false);
        }

        if (!textInputMode) {
            map = Gdx.input.isKeyJustPressed(Keys.M);
            info = Gdx.input.isKeyJustPressed(Keys.I);
            sc = Gdx.input.isKeyJustPressed(Keys.PRINT_SCREEN);
        }

        if (sc) {
            Pixmap pixmap = Pixmap.createFromFrameBuffer(
                    0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
            ByteBuffer pixels = pixmap.getPixels();

            // This loop makes sure the whole screenshot is opaque and looks exactly like what the user is seeing
            int size = Gdx.graphics.getBackBufferWidth() * Gdx.graphics.getBackBufferHeight() * 4;
            for (int i = 3; i < size; i += 4) {
                pixels.put(i, (byte) 255);
            }

            PixmapIO.writePNG(Gdx.files.local("screen.png"), pixmap, Deflater.DEFAULT_COMPRESSION, true);
            pixmap.dispose();
        }
    }

    public static Vector2 getProjectedMousePos() {
        Ray ray = MousseAdventure.cam.getPickRay(mx, Gdx.graphics.getHeight() - my);

        float t = -ray.origin.z/ray.direction.z;
        float convertedX = ray.origin.x+ray.direction.x*t;
        float convertedY = ray.origin.y+ray.direction.y*t;

        return new Vector2((int) convertedX, (int) convertedY);
    }
}
