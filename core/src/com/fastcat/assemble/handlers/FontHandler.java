package com.fastcat.assemble.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Bitmap;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.handlers.FontHandler.FontData;
import com.fastcat.assemble.interfaces.OnScaleUpdated;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.badlogic.gdx.graphics.Color.*;
import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import static com.fastcat.assemble.handlers.InputHandler.scaleX;
import static com.fastcat.assemble.handlers.InputHandler.scaleY;

public final class FontHandler implements Disposable {

    //Font File
    private static final FreeTypeFontGenerator medium = new FreeTypeFontGenerator(Gdx.files.internal("font/bold.ttf"));
    private static final FreeTypeFontGenerator bold = new FreeTypeFontGenerator(Gdx.files.internal("font/extra.ttf"));

    //Preload Fonts
    public static final FontData LOGO = new FontData(100, false, FontType.BOLD);
    public static final FontData TURN_CHANGE = new FontData(40, false, FontType.BOLD);
    public static final FontData MAIN = new FontData(60, WHITE);
    public static final FontData MAIN_GREY = new FontData(60, GRAY);
    public static final FontData ROLL = new FontData(40, false);
    public static final FontData NB30 = new FontData(30, false);
    public static final FontData NB26 = new FontData(26, false);
    public static final FontData CARD_NAME = new FontData(24, false, FontType.BOLD);
    public static final FontData CARD_DESC = new FontData(21, false);
    public static final FontData SUB_NAME = new FontData(22, WHITE, false, false, FontType.BOLD);
    public static final FontData SUB_DESC = new FontData(20, WHITE, false, false);
    public static final FontData SYN_NAME = new FontData(22, WHITE, false, false, FontType.BOLD);
    public static final FontData SYN_DESC = new FontData(20, new Color(0.9f, 0.9f, 0.9f, 1.0f), false, false, FontType.BOLD);
    public static final FontData HEALTH = new FontData(20, true, FontType.BOLD);

    public static final BitmapFont BF_CARD_NAME = generate(26, new Color(1, 1, 1, 1), new Color(0.2f, 0.2f, 0.2f, 1), true, false, FontType.BOLD);
    public static final BitmapFont BF_CARD_DESC = generate(24, new Color(1, 1, 1, 1), new Color(0.2f, 0.2f, 0.2f, 1), true, false);
    public static final BitmapFont BF_SUB_DESC = generate(20, new Color(1, 1, 1, 1), new Color(0.2f, 0.2f, 0.2f, 1), false, false);
    public static final BitmapFont BF_NB30 = generate(30, new Color(1, 1, 1, 1), new Color(0.2f, 0.2f, 0.2f, 1), true, false);

    //GlyphLayout
    public static final GlyphLayout layout = new GlyphLayout();

    //Pattern
    public static final Pattern COLOR_PATTERN = Pattern.compile("&([a-z])<([^>]*)>");
    public static final Pattern VAR_PATTERN = Pattern.compile("\\{([ADVX])\\}");

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
        BitmapFont f = medium.generateFont(parameter);
        f.getData().markupEnabled = true;
        return f;
    }

    public static BitmapFont generate(int size, boolean border, FontType type) {
        return generate(size, WHITE, DARK_GRAY, border, type);
    }

    public static BitmapFont generate(int size, Color color, Color bColor, boolean border, FontType type) {
        return generate(size, color, bColor, true, border, type);
    }

    public static BitmapFont generate(int size, Color color, Color bColor, boolean shadow, boolean border, FontType type) {
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.characters = "";
        parameter.incremental = true;
        parameter.shadowOffsetX = parameter.shadowOffsetY = shadow ? 2 : 0;
        parameter.size = size;
        parameter.color = color;
        parameter.borderColor = bColor;
        parameter.borderWidth = border ? parameter.size * 0.04f : 0.0f;
        FreeTypeFontGenerator g = type == FontType.MEDIUM ? medium : bold;
        BitmapFont f = g.generateFont(parameter);
        f.getData().markupEnabled = true;
        return f;
    }

    public static void renderCenter(SpriteBatch sb, FontData font, String text, float x, float y) {
        layout.setText(font.font, text);
        font.draw(sb, layout, font.alpha, x - layout.width / 2, y + layout.height * 0.54f);
    }

    public static void renderCenter(SpriteBatch sb, FontData fontData, String text, float x, float y, float bw) {
        BitmapFont font = fontData.font;
        layout.setText(font, text, fontData.color, bw, Align.center, false);
        float ry = y + (layout.height) * 0.54f;
        fontData.draw(sb, layout, fontData.alpha, x, ry);
    }

    public static void renderCenterWrap(SpriteBatch sb, FontData fontData, String text, float x, float y, float bw, float bh) {
        BitmapFont font = fontData.font;
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
        layout.setText(font, text, fontData.color, bw, Align.left, false);
        float ry = y + (layout.height) * 0.54f;
        fontData.draw(sb, layout, fontData.alpha, x, ry);
    }

    public static void renderMemberName(
            SpriteBatch sb, FontData fontData, String text, float x, float y, float bw) {
        BitmapFont font = fontData.font;
        layout.setText(font, text, fontData.color, bw, Align.left, false);
        float ry = y + (layout.height) * 0.5f;
        fontData.draw(sb, layout, fontData.alpha, x, ry);
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
        layout.setText(font, text, fontData.color, bw, Align.center, true);
        float ry = y + (layout.height) * 0.65f;
        fontData.draw(sb, layout, fontData.alpha, x, ry);
    }

    private static void renderLine(SpriteBatch sb, FontData fontData, String text, float x, float y, float bw) {
        BitmapFont font = fontData.font;
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
        medium.dispose();
        bold.dispose();
        TURN_CHANGE.dispose();
        LOGO.dispose();
        ROLL.dispose();
        NB26.dispose();
        NB30.dispose();
    }

    public static class FontData implements Disposable, OnScaleUpdated {
        public BitmapFont font;
        public Color color;
        public Color bColor;
        private int originSize;
        public int size;
        public float scale = 1.0f;
        public float alpha = 1.0f;
        public boolean shadow;
        public boolean border;
        public FontType type;

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
            originSize = size;
            this.size = (int) (originSize * InputHandler.scaleA);
            this.color = color.cpy();
            this.bColor = bColor.cpy();
            this.font = generate(this.size, this.color, this.bColor, shadow, border);
            this.font.getData().markupEnabled = true;
            this.shadow = shadow;
            this.border = border;
            this.type = FontType.MEDIUM;
            InputHandler.scaleUpdateListener.add(this);
        }

        public FontData(int size, Color color, boolean shadow, boolean border, FontType type) {
            this(size, shadow, border, color, new Color(0.2f, 0.2f, 0.2f, 1), type);
        }

        public FontData(int size, boolean border, FontType type) {
            this(size, true, border, new Color(1, 1, 1, 1), new Color(0.2f, 0.2f, 0.2f, 1), type);
        }

        public FontData(int size, boolean shadow, boolean border, FontType type) {
            this(size, shadow, border, new Color(1, 1, 1, 1), new Color(0.2f, 0.2f, 0.2f, 1), type);
        }

        public FontData(int size, Color color, FontType type) {
            this(size, true, false, color, new Color(0.2f, 0.2f, 0.2f, 1), type);
        }

        public FontData(int size, boolean shadow, boolean border, Color color, Color bColor, FontType type) {
            originSize = size;
            this.size = (int) (originSize * InputHandler.scaleA);
            this.color = color.cpy();
            this.bColor = bColor.cpy();
            this.font = generate(this.size, this.color, this.bColor, shadow, border, type);
            this.font.getData().markupEnabled = true;
            this.shadow = shadow;
            this.border = border;
            this.type = type;
            InputHandler.scaleUpdateListener.add(this);
        }

        public final FontData cpy() {
            return new FontData(originSize, shadow, border, new Color(color), new Color(bColor), type);
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
            InputHandler.scaleUpdateListener.remove(this);
        }

        @Override
        public void onScaleUpdated() {
            font.dispose();
            font = generate(this.originSize, this.color, this.bColor, shadow, border, type);
        }
    }

    public enum FontType {
        MEDIUM, BOLD
    }
}
