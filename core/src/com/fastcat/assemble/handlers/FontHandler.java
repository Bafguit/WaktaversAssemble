package com.fastcat.assemble.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.fastcat.assemble.abstracts.AbstractMember;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.badlogic.gdx.graphics.Color.*;
import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import static com.fastcat.assemble.handlers.InputHandler.scaleX;
import static com.fastcat.assemble.handlers.InputHandler.scaleY;

public final class FontHandler implements Disposable {

    //Font File
    private static final FreeTypeFontGenerator font = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));

    //Preload Fonts
    public static final FontData LOGO = new FontData(100, false);
    public static final FontData TURN_CHANGE = new FontData(60, false);
    public static final FontData ROLL = new FontData(40, false);
    public static final FontData NB30 = new FontData(30, false);
    public static final FontData NB26 = new FontData(26, false);
    public static final FontData SUB_NAME = new FontData(24, false);
    public static final FontData SUB_DESC = new FontData(21, false);

    //GlyphLayout
    public static final GlyphLayout layout = new GlyphLayout();

    //Pattern
    public static final Pattern COLOR_PATTERN = Pattern.compile("&([a-z])<([^>]*)>");
    private static final Pattern VAR_PATTERN = Pattern.compile("\\{([ADVX])\\}");

    /***
     * Instance of handler.
     * Initialized on getInstance()
     */
    private static FontHandler instance;

    /***
     * Returns instance of handler, if not exist, create new.
     * @return instance of handler
     */
    public static FontHandler getInstance() {
        if (instance == null) return (instance = new FontHandler());

        return instance;
    }

    public FontHandler() {}

    public static BitmapFont generate(int size, boolean border) {
        return generate(size, WHITE, DARK_GRAY, border);
    }

    public static BitmapFont generate(int size, Color color, Color bColor, boolean border) {
        return generate(size, color, bColor, true, border);
    }

    public static BitmapFont generate(int size, Color color, Color bColor, boolean shadow, boolean border) {
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.characters = "";
        parameter.incremental = true;
        parameter.shadowOffsetX = parameter.shadowOffsetY = shadow ? 2 : 0;
        parameter.size = size;
        parameter.color = color;
        parameter.borderColor = bColor;
        parameter.borderWidth = border ? parameter.size * 0.04f : 0.0f;
        return font.generateFont(parameter);
    }

    public static void renderCenter(SpriteBatch sb, FontData font, String text, float x, float y) {
        font.font.getData().setScale(scaleX);
        layout.setText(font.font, text);
        font.draw(sb, layout, font.alpha, x - layout.width / 2, y + layout.height * 0.54f);
        font.font.getData().setScale(font.scale);
    }

    public static void renderCenter(SpriteBatch sb, FontData fontData, String text, float x, float y, float bw) {
        BitmapFont font = fontData.font;
        font.getData().setScale(scaleX);
        layout.setText(font, text, fontData.color, bw, Align.center, false);
        float ry = y + (layout.height) * 0.54f;
        fontData.draw(sb, layout, fontData.alpha, x, ry);
        font.getData().setScale(fontData.scale);
    }

    public static void renderCenterWrap(SpriteBatch sb, FontData fontData, String text, float x, float y, float bw, float bh) {
        BitmapFont font = fontData.font;
        font.getData().setScale(scaleX);
        float scale = 1;
        while (true) {
            layout.setText(font, text, fontData.color, bw, Align.center, true);
            if (layout.height > bh && scale > 0.5f) {
                scale *= 0.9f;
                font.getData().setScale(scale);
            } else {
                break;
            }
        }
        layout.setText(font, text, fontData.color, bw, Align.center, true);
        float ry = y + (layout.height) * 0.54f;
        fontData.draw(sb, layout, fontData.alpha, x, ry);
        font.getData().setScale(fontData.scale);
    }

    public static void renderLineLeft(
            SpriteBatch sb, FontData fontData, String text, float x, float y, float bw) {
        BitmapFont font = fontData.font;
        layout.setText(font, text, fontData.color, bw * scaleX, Align.left, false);
        float ry = y * scaleY + (layout.height) * 0.54f;
        fontData.draw(sb, layout, fontData.alpha, x * scaleX, ry);
    }

    public static void renderLineRight(
            SpriteBatch sb, FontData fontData, String text, float x, float y, float bw) {
        BitmapFont font = fontData.font;
        layout.setText(font, text, fontData.color, bw * scaleX, Align.right, false);
        float ry = y * scaleY + (layout.height) * 0.54f;
        fontData.draw(sb, layout, fontData.alpha, x * scaleX, ry);
    }

    public static void renderLineBotLeft(
            SpriteBatch sb, FontData fontData, String text, float x, float y, float bw) {
        BitmapFont font = fontData.font;
        layout.setText(font, text, fontData.color, bw * scaleX, Align.left, false);
        fontData.draw(sb, layout, fontData.alpha, x * scaleX, y * scaleY);
    }

    public static void renderLineTopLeft(
            SpriteBatch sb, FontData fontData, String text, float x, float y, float bw) {
        BitmapFont font = fontData.font;
        layout.setText(font, text, fontData.color, bw * scaleX, Align.topLeft, true);
        float ry = y * scaleY + (layout.height) * 0.54f;
        fontData.draw(sb, layout, fontData.alpha, x * scaleX, ry);
    }

    public static void renderKeywordCenter(
            SpriteBatch sb, FontData fontData, String text, float x, float y, float bw) {
        renderLine(sb, fontData, text, x, y, bw);
    }
/*
    public static void renderCardCenter(
            SpriteBatch sb, AbstractCard card, FontData fontData, String text, Vector2 vector, float bw) {
        renderCardCenter(sb, card, fontData, text, vector.x, vector.y, bw);
    }
*/

    public static void renderSubText(SpriteBatch sb, FontData fontData, String text, float x, float y, float bw, boolean wrap) {
        BitmapFont font = fontData.font;
        font.getData().setScale(fontData.scale * InputHandler.scaleA);
        layout.setText(font, text, fontData.color, bw, Align.bottomLeft, wrap);
        fontData.draw(sb, layout, fontData.alpha, x, y);
    }

    public static void renderColorLeft(SpriteBatch sb, FontData fontData, String text, float x, float y, float bw) {
        BitmapFont font = fontData.font;
        Matcher matcher = COLOR_PATTERN.matcher(text);
        while (matcher.find()) {
            String mt = matcher.group(1);
            String mmt = matcher.group(2);
            text = matcher.replaceFirst(getColorKey(mt) + mmt + getHexColor(fontData.color));
            matcher = COLOR_PATTERN.matcher(text);
        }
        font.getData().setScale(fontData.scale * InputHandler.scaleA);
        layout.setText(font, text, fontData.color, bw * scaleX, Align.topLeft, true);
        fontData.draw(sb, layout, fontData.alpha, x * scaleX, y * scaleY);
    }

    public static void renderColorCenter(SpriteBatch sb, FontData fontData, String text, float x, float y, float bw) {
        BitmapFont font = fontData.font;
        Matcher matcher = COLOR_PATTERN.matcher(text);
        while (matcher.find()) {
            String mt = matcher.group(1);
            String mmt = matcher.group(2);
            text = matcher.replaceFirst(getColorKey(mt) + mmt + getHexColor(fontData.color));
            matcher = COLOR_PATTERN.matcher(text);
        }
        font.getData().setScale(fontData.scale * InputHandler.scaleA);
        layout.setText(font, text, fontData.color, bw * scaleX, Align.center, true);
        float ry = y * scaleY + (layout.height) * 0.54f;
        fontData.draw(sb, layout, fontData.alpha, x * scaleX, ry);
    }
/*
    public static void renderCardLeft(
            SpriteBatch sb, AbstractCard card, FontData fontData, String text, float x, float y, float bw, float bh) {
        BitmapFont font = fontData.font;
        Matcher matcher = COLOR_PATTERN.matcher(text);
        while (matcher.find()) {
            String mt = matcher.group(1);
            String mmt = matcher.group(2);
            text = matcher.replaceFirst(getColorKey(mt) + mmt + getHexColor(fontData.color));
            matcher = COLOR_PATTERN.matcher(text);
        }
        matcher = VAR_PATTERN.matcher(text);
        while (matcher.find()) {
            String mt = matcher.group(1);
            text = matcher.replaceFirst(card.getKeyValue(mt) + getHexColor(fontData.color));
            matcher = VAR_PATTERN.matcher(text);
        }
        font.getData().setScale(fontData.scale);
        font.getData().setLineHeight(fontData.size * 1.3f);
        layout.setText(font, text, fontData.color, bw * scaleX, Align.topLeft, true);
        if(layout.runs.size * font.getLineHeight() > bh * scaleY) {
        	font.getData().setScale(font.getScaleY() * 0.8f);
        	layout.setText(font, text, fontData.color, bw * scaleX, Align.topLeft, true);
        }
        fontData.draw(sb, layout, fontData.alpha, x * scaleX, y * scaleX);
    }
*/
    public static void renderMemberDesc(
            SpriteBatch sb, AbstractMember member, FontData fontData, String text, float x, float y, float bw) {
        BitmapFont font = fontData.font;
        Matcher matcher = COLOR_PATTERN.matcher(text);
        while (matcher.find()) {
            String mt = matcher.group(1);
            String mmt = matcher.group(2);
            text = matcher.replaceFirst(getColorKey(mt) + mmt + getHexColor(fontData.color));
            matcher = COLOR_PATTERN.matcher(text);
        }
        matcher = VAR_PATTERN.matcher(text);
        while (matcher.find()) {
            String mt = matcher.group(1);
            text = matcher.replaceFirst(member.getKeyValue(mt) + getHexColor(fontData.color));
            matcher = VAR_PATTERN.matcher(text);
        }
        font.getData().setScale(fontData.scale * InputHandler.scaleA);
        layout.setText(font, text, fontData.color, bw * scaleX, Align.center, true);
        float ry = y * scaleY + (layout.height) * 0.65f;
        fontData.draw(sb, layout, fontData.alpha, x * scaleX, ry);
    }

    private static void renderLine(SpriteBatch sb, FontData fontData, String text, float x, float y, float bw) {
        BitmapFont font = fontData.font;
        font.getData().setScale(fontData.scale);
        layout.setText(font, text, fontData.color, bw * scaleX, Align.center, true);
        float ry = y * scaleY + (layout.height) * 0.65f;
        fontData.draw(sb, layout, fontData.alpha, x * scaleX, ry);
    }

    public static String getHexColor(Color color) {
        return "[" + String.format("#%06X", (0xFFFFFF & rgb888(color))) + "]";
    }

    public static String getColorKey(String key) {
        switch (key) {
            case "b":
                return getHexColor(CYAN);
            case "g":
                return getHexColor(CHARTREUSE);
            case "r":
                return getHexColor(SCARLET);
            case "y":
                return getHexColor(YELLOW);
            default:
                return getHexColor(WHITE);
        }
    }

    public void update() {
        // mainMenuFont.getData().setScale(InputHelper.scale);
    }

    @Override
    public void dispose() {
        font.dispose();
        TURN_CHANGE.dispose();
        LOGO.dispose();
        ROLL.dispose();
        NB26.dispose();
        NB30.dispose();
    }

    public static class FontData implements Disposable {
        public BitmapFont font;
        public Color color;
        public Color bColor;
        public int size;
        public float scale = 1.0f;
        public float alpha = 1.0f;
        public boolean shadow;
        public boolean border;

        public FontData(int size, Color color, boolean shadow, boolean border) {
            this(size, shadow, border, color, new Color(0.2f, 0.2f, 0.2f, 1));
        }

        public FontData(int size, boolean border) {
            this(size, true, border, new Color(1, 1, 1, 1), new Color(0.2f, 0.2f, 0.2f, 1));
        }

        public FontData(int size, boolean shadow, boolean border) {
            this(size, shadow, border, new Color(1, 1, 1, 1), new Color(0.2f, 0.2f, 0.2f, 1));
        }

        public FontData(int size, Color color) {
            this(size, true, false, color, new Color(0.2f, 0.2f, 0.2f, 1));
        }

        public FontData(int size, boolean shadow, boolean border, Color color, Color bColor) {
            this.size = size;
            this.color = color.cpy();
            this.bColor = bColor.cpy();
            this.font = generate(this.size, this.color, this.bColor, shadow, border);

            this.font.getData().markupEnabled = true;
            this.shadow = shadow;
            this.border = border;
        }

        public final FontData cpy() {
            return new FontData(size, shadow, border, new Color(color), new Color(bColor));
        }

        public final void draw(SpriteBatch sb, GlyphLayout layout, float alpha, float x, float y) {
            BitmapFontCache cache = font.getCache();
            cache.clear();
            cache.addText(layout, x, y);
            cache.draw(sb, alpha);
        }

        @Override
        public void dispose() {
            font.dispose();
        }
    }
}
