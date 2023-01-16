package com.fastcat.assemble.abstrcts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fastcat.assemble.WaktaAssemble;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.handlers.SoundHandler;

import static com.badlogic.gdx.graphics.Color.WHITE;
import static com.fastcat.assemble.handlers.FontHandler.*;
import static com.fastcat.assemble.handlers.InputHandler.*;

public abstract class AbstractUI implements Disposable {

    protected final Array<SubText> subs = new Array<>();
    public Array<SubText> subTexts;
    public AbstractUI parent;
    public SubText.SubWay subWay = SubText.SubWay.UP;
    public BasisType basis = BasisType.CENTER;
    public Sprite img;
    public float x;
    public float y;
    public float originX;
    public float originY;
    public float localX;
    public float localY;
    protected float cursorX;
    protected float cursorY;
    public float width;
    public float height;
    public float originWidth;
    public float originHeight;
    public boolean over;
    public boolean isPixmap = false;
    private boolean hasClick = false;
    public boolean hasOver = false;
    public boolean overable = true;
    public boolean enabled;
    public boolean showImg = true;

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
        originWidth = width;
        originHeight = height;
        this.width = width * scaleX;
        this.height = height * scaleY;
        originX = x;
        originY = y;
        setLocalPosition();
        over = false;
        enabled = true;
        uiScale = 1.0f;
        clicked = false;
        clicking = false;
    }

    public final void update() {
        width = originWidth * scaleX * uiScale;
        height = originHeight * scaleY * uiScale;
        setLocalPosition();
        if (parent != null) {
            x += parent.x;
            y += parent.y;
        }
        hasOver = mx > x && mx < x + width && my > y && my < y + height;
        if(isDesktop) {
            over = hasOver;
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

        if (enabled && !WaktaAssemble.fading) {

            if (over && isPixmap) {
                Color c = getSpritePixColor();
                over = c.a > 0;
            }

            if (over) {
                if (overable) {
                    subTexts = getSubText();
                    if (clicked) {
                        if (clickable) {
                            cursorX = mx - localX;
                            cursorY = my - localY;
                            if (!mute) SoundHandler.playSfx("CLICK");
                            onClick();
                        }
                    }
                    if (clicking) onClicking();
                    else if (hasClick) onClickEnd();
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
        sb.setColor(WHITE);
        renderUi(sb);
    }

    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if (overable && !over) sb.setColor(Color.LIGHT_GRAY);
            if (showImg) sb.draw(img, x, y, width, height);
        }
    }

    public final void renderSub(SpriteBatch sb) {
        if (subTexts != null) {
            if (subTexts.size > 0) {
                sb.setColor(WHITE);
                float sc, subP;
                if (subWay == SubText.SubWay.DOWN) {
                    sc = -10 * scaleY;
                    subP = y + sc;
                    for (SubText s : subTexts) {
                        subP = s.render(sb, x + width / 2, subP, subWay).y + sc;
                    }
                } else if (subWay == SubText.SubWay.UP) {
                    sc = 10 * scaleY;
                    subP = y + originHeight + sc;
                    for (SubText s : subTexts) {
                        subP = s.render(sb, x + width / 2, subP, subWay).y + sc;
                    }
                } else if (subWay == SubText.SubWay.LEFT) {
                    sc = -10 * scaleX;
                    subP = x + sc;
                    for (SubText s : subTexts) {
                        subP = s.render(sb, subP, y + width, subWay).x + sc;
                    }
                }
            }
        }
    }

    private void setLocalPosition(){
        if(trackable != TrackType.NONE && overable && clickable && clicking) {
            tracking = true;
            if(trackable == TrackType.CENTER) {
                localX = mx;
                localY = my;
            } else if(trackable == TrackType.CLICKED) {
                localX = mx - cursorX;
                localY = my - cursorY;
            }
        } else {
            tracking = false;
            localX = originX * scaleX;
            localY = originY * scaleY;
        }
        if(basis == BasisType.CENTER) {
            x = localX - width / 2;
            y = localY - height / 2;
        } else if(basis == BasisType.CENTER_TOP) {
            x = localX - width / 2;
            y = localY - height;
        } else if(basis == BasisType.CENTER_BOTTOM) {
            x = localX - width / 2;
            y = localY;
        } else if(basis == BasisType.CENTER_LEFT) {
            x = localX;
            y = localY - height / 2;
        } else if(basis == BasisType.CENTER_RIGHT) {
            x = localX - width;
            y = localY - height / 2;
        } else if(basis == BasisType.TOP_LEFT) {
            x = localX;
            y = localY - height;
        } else if(basis == BasisType.TOP_RIGHT) {
            x = localX - width;
            y = localY - height;
        } else if(basis == BasisType.BOTTOM_RIGHT) {
            x = localX - width;
            y = localY;
        } else {
            x = localX;
            y = localY;
        }
    }

    public Color getSpritePixColor() {
        Texture texture = img.getTexture();

        int LocalX = (int) ((mx - x) / scaleX);
        // we need to "invert" Y, because the screen coordinate origin is top-left
        int LocalY = (int) (((Gdx.graphics.getHeight() - my) - (Gdx.graphics.getHeight() - (y + height))) / scaleY);

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

    protected Array<SubText> getSubText() {
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
        setX(x);
        setY(y);
    }

    public void setScale(float scale) {
        uiScale = scale;
        width = originWidth * scaleX * uiScale;
        height = originHeight * scaleY * uiScale;
    }

    protected void trackCursor(boolean center) {
        if (trackable != TrackType.NONE && isCursorInScreen) {
            tracking = true;
            if (center) setPosition(mx - width / 2, my - height / 2);
            else setPosition(mx - cursorX, my - cursorY);
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

    protected void updateButton() {}

    protected void onClick() {}

    protected void onClicking() {}

    protected void onClickEnd() {}

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
        CENTER_TOP,
        CENTER_BOTTOM,
        CENTER_LEFT,
        CENTER_RIGHT,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
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
            top = new TempUI(FileHandler.ui.get("TEMP"));
            mid = new TempUI(FileHandler.ui.get("TEMP"));
            bot = new TempUI(FileHandler.ui.get("TEMP"));
            nameLayout = new GlyphLayout();
            descLayout = new GlyphLayout();
            nameFont = SUB_NAME;
            descFont = SUB_DESC;
            nameFont.font.getData().setScale(nameFont.scale);
            descFont.font.getData().setScale(descFont.scale);
            nameLayout.setText(nameFont.font, name, nameFont.color, mid.originWidth * 0.94f, Align.left, false);
            descLayout.setText(descFont.font, desc, descFont.color, mid.originWidth * 0.94f, Align.bottomLeft, true);
            line = descLayout.runs.size;
            ww = mid.width;
            hh = bot.height;
            mh = mid.height * 0.35f + descLayout.height;
        }

        public SubText(Sprite icon, String name, String desc) {
            this(name, desc);
            this.icon = new TempUI(icon);
        }

        public Vector2 render(SpriteBatch sb, float x, float y, SubWay way) {
            float xx = 0, yy = 0;
            if (way == SubWay.UP) {
                xx = x - ww * 0.5f;
                yy = 0;
                sb.draw(bot.img, xx, y, ww, hh);
                sb.draw(mid.img, xx, y + (yy += hh), ww, mh);
                sb.draw(top.img, xx, y + (yy += mh), ww, hh);
                yy += hh;
                float ny = y + yy - ww * 0.03f, dy = ny - nameLayout.height * 1.5f;
                nameFont.draw(sb, nameLayout, nameFont.alpha, x - ww * 0.47f, ny);
                descFont.draw(sb, descLayout, descFont.alpha, x - ww * 0.47f, dy);
            } else if (way == SubWay.DOWN) {
                xx = x - ww * 0.5f;
                yy = 0;
                sb.draw(top.img, xx, y + (yy -= hh), ww, hh);
                sb.draw(mid.img, xx, y + (yy -= mh), ww, mh);
                sb.draw(bot.img, xx, y + (yy -= hh), ww, hh);
                float ny = y - ww * 0.03f, dy = ny - nameLayout.height * 1.5f;
                nameFont.draw(sb, nameLayout, nameFont.alpha, x - ww * 0.47f, ny);
                descFont.draw(sb, descLayout, descFont.alpha, x - ww * 0.47f, dy);
            } else if (way == SubWay.LEFT) {
                xx = x - ww;
                yy = 0;
                sb.draw(top.img, xx, y + (yy -= hh), ww, hh);
                sb.draw(mid.img, xx, y + (yy -= mh), ww, mh);
                sb.draw(bot.img, xx, y + (yy -= hh), ww, hh);
                float ny = y - ww * 0.03f, dy = ny - nameLayout.height * 1.5f;
                nameFont.draw(sb, nameLayout, nameFont.alpha, xx + ww * 0.03f, ny);
                descFont.draw(sb, descLayout, descFont.alpha, xx + ww * 0.03f, dy);
            } else if (way == SubWay.RIGHT) {
                xx = x;
                yy = 0;
                sb.draw(top.img, xx, y + (yy -= hh), ww, hh);
                sb.draw(mid.img, xx, y + (yy -= mh), ww, mh);
                sb.draw(bot.img, xx, y + (yy -= hh), ww, hh);
                float ny = y - ww * 0.03f, dy = ny - nameLayout.height * 1.5f;
                nameFont.draw(sb, nameLayout, nameFont.alpha, xx + ww * 0.03f, ny);
                descFont.draw(sb, descLayout, descFont.alpha, xx + ww * 0.03f, dy);
                xx += ww;
            }
            return new Vector2(xx, y + yy);
        }

        public enum SubWay {
            DOWN,
            UP,
            RIGHT,
            LEFT
        }
    }
}
