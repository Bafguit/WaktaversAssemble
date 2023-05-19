package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.handlers.SoundHandler;
import com.fastcat.assemble.utils.Vector2i;

import java.util.regex.Matcher;

import static com.badlogic.gdx.graphics.Color.WHITE;
import static com.fastcat.assemble.handlers.FontHandler.*;
import static com.fastcat.assemble.handlers.InputHandler.*;

public abstract class AbstractUI implements Disposable, Cloneable {

    protected SubText subs;
    public SubText subTexts;
    public AbstractUI parent;
    public SubText.SubWay subWay = SubText.SubWay.UP;
    public BasisType basis = BasisType.CENTER;
    public Sprite img;
    public Vector2i pos;
    public float x;
    public float y;
    public float z;
    public float originX;
    public float originY;
    public float originZ;
    public float localX;
    public float localY;
    public float localZ;
    protected float cursorX;
    protected float cursorY;
    public float width;
    public float height;
    public final float realWidth;
    public final float realHeight;
    public float originWidth;
    public float originHeight;
    private float fromX;
    private float fromY;
    private float fromZ;
    private float toX;
    private float toY;
    private float toZ;
    private float distCount;
    public boolean over;
    public boolean fluid = false;
    public boolean fluiding = false;
    public boolean isPixmap = false;
    public boolean hasOver = false;
    public boolean overable = true;
    public boolean clickEnd = true;
    public boolean enabled;
    public boolean showImg = true;
    public boolean is3D = false;
    private boolean hasClick = false;

    public Vector2 mouse;

    public float uiScale;
    public boolean clicked;
    public boolean clicking;
    public boolean clickable = true;
    public boolean tracking = false;
    public boolean mute = false;
    public TrackType trackable = TrackType.NONE;

    public AbstractUI(Sprite texture) {
        this(texture, -10000, -10000);
    }

    public AbstractUI(Sprite texture, float x, float y) {
        this(texture, x, y, texture.getWidth(), texture.getHeight());
    }

    public AbstractUI(Sprite img, float x, float y, float width, float height) {
        this.img = new Sprite(img);
        realWidth = originWidth = width;
        realHeight = originHeight = height;
        this.width = width * scaleA;
        this.height = height * scaleA;
        originX = x;
        originY = y;
        pos = new Vector2i(0, 0);
        setLocalPosition();
        mouse = new Vector2(mx, my);
        over = false;
        enabled = true;
        uiScale = 1.0f;
        clicked = false;
        clicking = false;
    }

    public final void update() {
        foreUpdate();
        width = originWidth * scaleA;
        height = originHeight * scaleA;
        if(is3D) mouse = InputHandler.getProjectedMousePos();
        else mouse.set(mx, my);
        if(fluid && fluiding) {
            fluidPosition();
        }
        setLocalPosition();
        if (parent != null) {
            x += parent.x;
            y += parent.y;
        }
        hasOver = mouse.x > x && mouse.x < x + width && mouse.y > y && mouse.y < y + height;
        if(isDesktop) {
            over = hasOver && isCursorInScreen;
            clicked = over && isLeftClick;
            hasClick = clicked || clicking;
            clicking = over && hasClick && isLeftClicking;
        } else {
            clicked = hasClick && hasOver && isLeftClick;
            clicking = hasClick && hasOver && isLeftClicking;
            over = hasClick;
            if(isLeftClick) {
                hasClick = hasOver && !clicked;
            }
        }

        if (enabled && !MousseAdventure.fading) {

            if (over && isPixmap) {
                Color c = getSpritePixColor();
                over = c.a > 0;
            }

            if (over) {
                if (overable) {
                    subTexts = getSubText();
                    if (clicked) {
                        if (clickable) {
                            cursorX = mouse.x - localX;
                            cursorY = mouse.y - localY;
                            if (!mute) SoundHandler.playSfx("CLICK");
                            onClick();
                        }
                    }
                    if (clicking) onClicking();
                    else if (hasClick && clickEnd) onClickEnd();
                } else over = false;
            }
            updateButton();
        } else {
            over = false;
            hasOver = false;
            hasClick = false;
            clicked = false;
        }
    }

    public final void render(SpriteBatch sb) {
        if(is3D) sb.setProjectionMatrix(MousseAdventure.cam.combined);
        else sb.setProjectionMatrix(MousseAdventure.camera.combined);
        sb.setColor(WHITE);
        renderUi(sb);
        //renderSub(sb);
    }

    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if (overable && !over) img.setColor(Color.LIGHT_GRAY);
            if (showImg) {
                sb.draw(img, x, y, width, height);
            }
        }
    }

    public final void renderSub(SpriteBatch sb) {
        sb.setColor(WHITE);
        if (subTexts != null) {
            img.setColor(WHITE);
            float sc, subP;
            if (subWay == SubText.SubWay.DOWN) {
                sc = -10 * scaleA;
                subP = y + sc;
                subTexts.render(sb, x + width / 2, subP, subWay);
            } else if (subWay == SubText.SubWay.UP) {
                sc = 10 * scaleA;
                subP = y + height + sc;
                subTexts.render(sb, x + width / 2, subP, subWay);
            }
        }
    }

    private void fluidPosition() {
        distCount += MousseAdventure.tick * 10;
        if(distCount > 1) {
            distCount = 1;
            fluiding = false;
        }
        originX = Interpolation.circleOut.apply(fromX, toX, distCount);
        originY = Interpolation.circleOut.apply(fromY, toY, distCount);
    }

    protected void setLocalPosition(){
        if(trackable != TrackType.NONE && over && clickable && clicking) {
            tracking = true;
            if(trackable == TrackType.CENTER) {
                localX = mouse.x;
                localY = mouse.y;
            } else if(trackable == TrackType.CLICKED) {
                localX = mouse.x - cursorX;
                localY = mouse.y - cursorY;
            }
        } else {
            tracking = false;
            localX = originX * scaleX;
            localY = originY * scaleY;
        }

        x = basis.getPosX(localX, width);
        y = basis.getPosY(localY, height);
    }

    public Color getSpritePixColor() {
        Texture texture = img.getTexture();

        int LocalX = (int) ((mouse.x - x) / scaleX);
        // we need to "invert" Y, because the screen coordinate origin is top-left
        int LocalY = (int) (((Gdx.graphics.getHeight() - mouse.y) - (Gdx.graphics.getHeight() - (y + height))) / scaleY);

        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        Pixmap pixmap = texture.getTextureData().consumePixmap();
        Color c = new Color(pixmap.getPixel(LocalX, LocalY));
        pixmap.dispose();
        return c;
    }

    public void setParent(AbstractUI ui) {
        parent = ui;
        setLocalPosition();
    }

    protected SubText getSubText() {
        return subs;
    }

    public void setX(float x) {
        originX = x;
        setLocalPosition();
    }

    public void setY(float y) {
        originY = y;
        setLocalPosition();
    }

    public void setPosition(float x, float y) {
        if(!fluiding) {
            if(fluid) {
                toX = x;
                fromX = originX;
                toY = y;
                fromY = originY;

                fluiding = true;
                distCount = 0;
            } else {
                originX = x;
                originY = y;
                setLocalPosition();
            }
        }
    }

    public void setScale(float scale) {
        uiScale = scale;
        originWidth = realWidth * uiScale;
        originHeight = realHeight * uiScale;
        width = originWidth * scaleA;
        height = originHeight * scaleA;
    }

    protected void trackCursor(boolean center) {
        if (trackable != TrackType.NONE && isCursorInScreen) {
            tracking = true;
            if (center) setPosition(mouse.x - width / 2, mouse.y - height / 2);
            else setPosition(mouse.x - cursorX, mouse.y - cursorY);
        }
    }

    public void show() {}

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isOverlap(AbstractUI ui) {
        return x < ui.x + ui.width && x + width > ui.x && y < ui.y + ui.height && y + height > ui.y;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public void pix() {
        isPixmap = true;
    }

    public void dispose() {}

    public void onHide() {
        over = false;
        showImg = false;
        clicked = false;
        clicking = false;
    }

    protected void foreUpdate() {}

    protected void updateButton() {}

    protected void onClick() {}

    protected void onClicking() {}

    protected void onClickEnd() {}

    @Override
    public AbstractUI clone() {
        try {
            return (AbstractUI) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public static class TempUI extends AbstractUI {
        public TempUI(Sprite texture) {
            super(texture);
            overable = false;
        }

        public TempUI(Sprite texture, float x, float y) {
            super(texture, x, y);
            overable = false;
        }

        public TempUI(Sprite texture, float x, float y, float w, float h) {
            super(texture, x, y, w, h);
            overable = false;
        }
    }

    public enum BasisType {
        CENTER,
        TOP,
        BOTTOM,
        CENTER_LEFT,
        CENTER_RIGHT,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT;

        public float getPosX(float localX, float width) {
            switch(this) {
                case CENTER:
                case TOP:
                case BOTTOM:
                    return localX - width / 2;
                case CENTER_RIGHT:
                case TOP_RIGHT:
                case BOTTOM_RIGHT:
                    return localX - width;
                default:
                    return localX;
            }
        }

        public float getPosY(float localY, float height) {
            switch(this) {
                case CENTER:
                case CENTER_LEFT:
                case CENTER_RIGHT:
                    return localY - height / 2;
                case TOP:
                case TOP_LEFT:
                case TOP_RIGHT:
                    return localY - height;
                default:
                    return localY;
            }
        }
    }

    public enum TrackType {
        NONE,
        CENTER,
        CLICKED
    }

    public static class SubText {
        private final GlyphLayout nameLayout;
        private final GlyphLayout descLayout;
        private final TempUI top;
        private final TempUI mid;
        private final TempUI bot;
        private final FontData nameFont;
        private final FontData descFont;
        public TempUI icon;
        public String name;
        public String desc;
        public int line;
        public float ww, hh, mh;

        public SubText(String name, String desc) {
            this.name = name;
            this.desc = desc;
            top = new TempUI(FileHandler.ui.get("SUB_TOP"));
            mid = new TempUI(FileHandler.ui.get("SUB_MID"));
            bot = new TempUI(FileHandler.ui.get("SUB_BOT"));
            nameLayout = new GlyphLayout();
            descLayout = new GlyphLayout();
            nameFont = SUB_NAME;
            descFont = SUB_DESC;
        }

        public void render(SpriteBatch sb, float x, float y, SubWay way) {
            mid.update();
            top.update();
            bot.update();
            String n = getColorKey("y") + name, d = desc;
            Matcher matcher = COLOR_PATTERN.matcher(d);
            while (matcher.find()) {
                String mt = matcher.group(1);
                String mmt = matcher.group(2);
                d = matcher.replaceFirst(getColorKey(mt) + mmt + getHexColor(nameFont.color));
                matcher = COLOR_PATTERN.matcher(d);
            }
            nameFont.font.getData().setScale(nameFont.scale * scaleA);
            descFont.font.getData().setScale(descFont.scale * scaleA);
            nameLayout.setText(nameFont.font, n, nameFont.color, mid.width * 0.92f, Align.bottomLeft, true);
            descLayout.setText(descFont.font, d, descFont.color, mid.width * 0.92f, Align.bottomLeft, true);
            nameFont.font.getData().setScale(nameFont.scale);
            descFont.font.getData().setScale(descFont.scale);
            line = descLayout.runs.size;
            ww = mid.width;
            hh = bot.height;
            mh = mid.height * 0.4f + descLayout.height + nameLayout.height * 1.5f;
            if(way == SubWay.DOWN) y -= (hh + hh + mh);
            float tw = ww * 0.5f, sc = 10 * scaleY, w = Gdx.graphics.getWidth();
            if((x - tw) < sc) {
                x = sc;
            } else if((x + tw) > (w - sc)) {
                x = w - sc - ww;
            } else {
                x -= tw;
            }
            float yy = 0;
            sb.draw(bot.img, x, y, ww, hh);
            sb.draw(mid.img, x, y + (yy += hh), ww, mh);
            sb.draw(top.img, x, y + (yy += mh), ww, hh);
            yy += hh;
            float ny = y + mid.height + mh, dy = y + mid.height + (mh - nameLayout.height * 1.5f);
            renderSubText(sb, nameFont, n, x + ww * 0.04f, ny, mid.width * 0.92f, false);
            renderSubText(sb, descFont, d, x + ww * 0.04f, dy, mid.width * 0.92f, true);
        }

        public enum SubWay {
            DOWN,
            UP
        }
    }
}
