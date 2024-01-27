package com.fastcat.assemble.screens.battle;

import static com.fastcat.assemble.handlers.FontHandler.VAR_PATTERN;
import static com.fastcat.assemble.handlers.FontHandler.getHexColor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.abstracts.AbstractUI.SubText.SubWay;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.screens.battle.SynergyDisplay.SynergyDisplayType;
import java.util.regex.Matcher;

public class MemberDisplay extends AbstractUI {

    private final FontHandler.FontData fontName = FontHandler.CARD_DESC.cpy();
    private final FontHandler.FontData fontDesc = FontHandler.CARD_DESC.cpy();
    private final AbstractUI.TempUI cardImg, tile, descBg; 
    private final SynergyDisplay[] synergy;
    private final Sprite frame;

    public final AbstractMember member;
    public boolean isCard;
    public int cardIndex = -1;

    public boolean isUsing = false;

    public MemberDisplay(AbstractMember member) {
        //285x428
        super(FileHandler.getTexture("ui/cardBg"));
        this.member = member;
        clickable = true;
        basis = BasisType.CENTER;
        //tile 206x87
        tile = new AbstractUI.TempUI(FileHandler.getTexture("ui/memberTile"));
        tile.overable = true;
        tile.pix();
        //frame 258x349
        frame = new Sprite(FileHandler.getTexture("ui/cardFrame"));
        cardImg = new AbstractUI.TempUI(member.img);
        //descBg 258x200
        descBg = new AbstractUI.TempUI(FileHandler.getTexture("ui/cardDesc"));
        descBg.basis = BasisType.BOTTOM;
        synergy = new SynergyDisplay[member.synergy.length];
        for(int i = 0; i < member.synergy.length; i++) {
            SynergyDisplay d = new SynergyDisplay(member.synergy[i], SynergyDisplayType.card);
            d.member = this;
            d.overable = false;
            d.setScale(0.7f);
            synergy[i] = d;
        }
        isCard = true;
        fluid = true;
        fluidDuration = 0.05f;
    }

    @Override
    protected void foreUpdate() {
        clickable = fluid = isCard && WakTower.game.battle.members.size < WakTower.game.memberLimit && !isUsing; 
        if(isCard) {
            basis = BasisType.CENTER;
            subWay = SubWay.UP;
        } else {
            basis = BasisType.BOTTOM;
            subWay = SubWay.RIGHT;
        }
    }

    @Override
    protected SubText getSubText() {
        if(!isCard && tile.over) {
            String n = member.tempClone.getName(), d = member.tempClone.desc;
            Matcher matcher = VAR_PATTERN.matcher(d);
            while (matcher.find()) {
                String mt = matcher.group(1);
                d = matcher.replaceFirst(member.getKeyValue(mt) + getHexColor(Color.WHITE));
                matcher = VAR_PATTERN.matcher(d);
            }
            subs = new SubText(n, d);
        } else subs = null;
        return subs;
    }

    @Override
    protected void updateButton() {
        if(!isCard) {
            tile.setPosition(originX, originY);
            tile.update();
            if(tile.over) {
                InputHandler.alreadyOver = true;
                if(tile.timer == 1f) {
                    subs = new SubText(member.tempClone);
                }
            } else subs = null;
        } else {
            if(over) InputHandler.alreadyOver = true;
            cardImg.setPosition(originX, originY - originHeight * 0.5f + 14 + cardImg.originHeight * 0.5f);
            for(int i = 0; i < synergy.length; i++) {
                SynergyDisplay s = synergy[i];
                float rx = localX / InputHandler.scaleX;
                float ry = localY / InputHandler.scaleY;
                s.overOnlyOne = !over;
                s.overable = over;
                s.setPosition(rx + originWidth * 0.4f - s.originWidth * (synergy.length - 1 - i), ry + originHeight * 0.433f);
                s.update();
            }
            descBg.setPosition(originX, cardImg.originY - cardImg.originHeight * 0.5f);
            descBg.update();
        }
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if(isCard) {
            sb.draw(img, x, y, width, height);
            float xx = x + width * 0.5f - cardImg.width * 0.5f;
            float yy = y + height * 0.5f - cardImg.height * 0.5f - 28 * InputHandler.scaleA;
            sb.draw(cardImg.img, xx, yy, cardImg.width, cardImg.height);
            
            if(timer > 0) {
                descBg.img.setBounds(xx, yy, descBg.width, descBg.height);
                descBg.img.draw(sb, timer);
                fontDesc.alpha = timer;
                FontHandler.renderMemberDesc(sb, member, fontDesc, member.desc, x + width * 0.1f, y + height * 0.215f, width * 0.8f);
            }
            sb.draw(frame, xx, yy, cardImg.width, cardImg.height);

            FontHandler.renderMemberName(sb, fontName, member.name, x + width * 0.08f, y + height * 0.935f, width);

            for(SynergyDisplay s : synergy) {
                s.render(sb);
            }
        } else {
            for(AbstractSynergy s : member.synergy) {
                SynergyDisplay sd = WakTower.battleScreen.synergyMap.get(s);
                if(sd.over) {
                    tile.render(sb);
                    break;
                }
            }

            member.animation.pos.set(localX, localY);
            //member.animation.render(sb);

            fontName.alpha = tile.timer;
            FontHandler.renderCenter(sb, fontName, member.name, x + width * 0.5f, y);

            if(timer == 1f) {
                
            }
        }
    }
/*
    @Override
    protected void onOver() {
        if(isCard) WakTower.battleScreen.updateHandPosition();
    }

    @Override
    protected void onOverEnd() {
        if(isCard) WakTower.battleScreen.updateHandPosition();
    }
*/

    @Override
    protected void onClick() {
        WakTower.battleScreen.clicked = this;
    }

    @Override
    protected void onClickEnd() {
        WakTower.battleScreen.clicked = null;
        if(y > height * 0.5f) {
            //ActionHandler.bot(new SummonMemberAction(this));
            isUsing = true;
        }
        WakTower.battleScreen.updateHandPosition();
    }

    @Override
    public void dispose() {
        fontName.dispose();
        fontDesc.dispose();
    }
}
