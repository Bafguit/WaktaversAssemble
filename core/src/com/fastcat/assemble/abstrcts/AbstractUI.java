package com.fastcat.assemble.abstrcts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.fastcat.assemble.MouseAdventure;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.handlers.SoundHandler;

import static com.badlogic.gdx.graphics.Color.WHITE;
import static com.fastcat.assemble.handlers.FontHandler.*;
import static com.fastcat.assemble.handlers.InputHandler.*;

public abstract class AbstractUI implements Disposable {

    protected SubText subs;
    public SubText subTexts;
    public AbstractUI parent;
    public SubText.SubWay subWay = SubText.SubWay.UP;
    public BasisType basis = BasisType.CENTER;
    public Sprite img;
    public Vector2 pos = new Vector2();
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
    public boolean is3D = true;
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

        if (enabled && !MouseAdventure.fading) {

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
        if(is3D) sb.setProjectionMatrix(MouseAdventure.cam.combined);
        else sb.setProjectionMatrix(MouseAdventure.camera.combined);
        sb.setColor(WHITE);
        renderUi(sb);
        renderSub(sb);
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
            } else if (subWay == SubText.SubWay.LEFT) {
                sc = -10 * scaleA;
                subP = x + sc;
                subTexts.render(sb, x + width / 2, subP, subWay);
            }
        }
    }

    private void fluidPosition() {
        distCount += MouseAdventure.tick * 10;
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
                setX(x);
                setY(y);
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
        private final GlyphLayout descLayout;
        private final TempUI top;
        private final TempUI mid;
        private final TempUI bot;
        private final FontData descFont;
        public TempUI icon;
        public String desc;
        public int line;
        public float ww, hh, mh;

        public SubText(String desc) {
            this.desc = desc;
            top = new TempUI(FileHandler.ui.get("SUB_TOP"));
            mid = new TempUI(FileHandler.ui.get("SUB_MID"));
            bot = new TempUI(FileHandler.ui.get("SUB_BOT"));
            descLayout = new GlyphLayout();
            descFont = SUB_DESC;
        }

        public SubText(Sprite icon, String desc) {
            this(desc);
            this.icon = new TempUI(icon);
        }

        public Vector2 render(SpriteBatch sb, float x, float y, SubWay way) {
            mid.update();
            top.update();
            bot.update();
            descFont.font.getData().setScale(scaleA);
            descLayout.setText(descFont.font, desc, descFont.color, mid.width * 0.94f, Align.bottomLeft, true);
            line = descLayout.runs.size;
            ww = mid.width;
            hh = bot.height;
            mh = mid.height * 0.35f + descLayout.height;
            float xx = 0, yy = 0;
            if (way == SubWay.UP) {
                xx = x - ww * 0.5f;
                yy = 0;
                sb.draw(bot.img, xx, y, ww, hh);
                sb.draw(mid.img, xx, y + (yy += hh), ww, mh);
                sb.draw(top.img, xx, y + (yy += mh), ww, hh);
                yy += hh;
                float dy = y + mid.height + mh * 0.9f;
                descFont.draw(MouseAdventure.application.sb, descLayout, descFont.alpha, x - descLayout.width * 0.5f, dy);
            } else if (way == SubWay.DOWN) {
                xx = x - ww * 0.5f;
                yy = 0;
                sb.draw(top.img, xx, y + (yy -= hh), ww, hh);
                sb.draw(mid.img, xx, y + (yy -= mh), ww, mh);
                sb.draw(bot.img, xx, y + (yy -= hh), ww, hh);
                float dy = y - ww * 0.03f;
                descFont.draw(MouseAdventure.application.sb, descLayout, descFont.alpha, x - ww * 0.47f, dy);
            } else if (way == SubWay.LEFT) {
                xx = x - ww;
                yy = 0;
                sb.draw(top.img, xx, y + (yy -= hh), ww, hh);
                sb.draw(mid.img, xx, y + (yy -= mh), ww, mh);
                sb.draw(bot.img, xx, y + (yy -= hh), ww, hh);
                float dy = y - ww * 0.03f;
                descFont.draw(MouseAdventure.application.sb, descLayout, descFont.alpha, xx + ww * 0.03f, dy);
            } else if (way == SubWay.RIGHT) {
                xx = x;
                yy = 0;
                sb.draw(top.img, xx, y + (yy -= hh), ww, hh);
                sb.draw(mid.img, xx, y + (yy -= mh), ww, mh);
                sb.draw(bot.img, xx, y + (yy -= hh), ww, hh);
                float dy = y - ww * 0.03f;
                descFont.draw(MouseAdventure.application.sb, descLayout, descFont.alpha, xx + ww * 0.03f, dy);
                xx += ww;
            }
            descFont.font.getData().setScale(descFont.scale);
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
