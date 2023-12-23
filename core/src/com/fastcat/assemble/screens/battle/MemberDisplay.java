package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.actions.SummonMemberAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.handlers.ScreenHandler;
import com.fastcat.assemble.screens.battle.SynergyDisplay.SynergyDisplayType;

public class MemberDisplay extends AbstractUI implements Disposable {

    private final FontHandler.FontData fontName = FontHandler.CARD_DESC.cpy();
    private final FontHandler.FontData fontDesc = FontHandler.CARD_DESC.cpy();
    private final AbstractUI.TempUI cardImg, tile, descBg; 
    private final SynergyDisplay[] synergy;
    private final Sprite frame;

    public final AbstractMember member;
    public boolean isCard;
    public int cardIndex = -1;

    public MemberDisplay(AbstractMember member) {
        //285x428
        super(FileHandler.getTexture("ui/cardBg"));
        this.member = member;
        clickable = true;
        basis = BasisType.CENTER;
        //tile 206x87
        tile = new AbstractUI.TempUI(FileHandler.getTexture("ui/memberTile"));
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
        clickable = fluid = isCard && WakTower.game.battle.members.size < WakTower.game.memberLimit; 
    }

    @Override
    protected void updateButton() {
        if(over) InputHandler.alreadyOver = true;
        tile.update();
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
                FontHandler.renderMemberDesc(sb, member, fontDesc, member.desc, x + width * 0.1f, y + height * 0.2f, width * 0.8f);
            }
            sb.draw(frame, xx, yy, cardImg.width, cardImg.height);

            FontHandler.renderMemberName(sb, fontName, member.name, x + width * 0.08f, y + height * 0.92f, width);

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
            member.animation.render(sb);

            fontName.alpha = timer;
            FontHandler.renderCenter(sb, fontName, member.name, x + width * 0.9f, y + height * 0.9f);

            if(timer == 1f) {
                //효과 설명 출력
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
        if(y > height) {
            ActionHandler.bot(new SummonMemberAction(member));
        }
        WakTower.battleScreen.updateHandPosition();
    }

    @Override
    public void dispose() {
        fontName.dispose();
        fontDesc.dispose();
    }
}
