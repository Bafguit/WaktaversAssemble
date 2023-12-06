package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.ScreenHandler;
import com.fastcat.assemble.screens.battle.SynergyDisplay.SynergyDisplayType;

public class MemberDisplay extends AbstractUI {

    private final FontHandler.FontData fontName = FontHandler.TURN_CHANGE.cpy();
    private final FontHandler.FontData fontDesc = FontHandler.TURN_CHANGE.cpy();
    private final AbstractUI.TempUI cardImg, tile, descBg; 
    private final SynergyDisplay[] synergy;
    private final Sprite frame;

    public final AbstractMember member;
    public boolean isCard;

    public MemberDisplay(AbstractMember member) {
        //228x342
        super(FileHandler.getTexture("ui/cardBg"));
        this.member = member;
        clickable = true;
        basis = BasisType.CENTER;
        tile = new AbstractUI.TempUI(FileHandler.getTexture("ui/memberTile"));
        frame = new Sprite(FileHandler.getTexture("ui/cardFrame"));
        cardImg = new AbstractUI.TempUI(member.img.getTexture());
        //descBg 높이: 140
        descBg = new AbstractUI.TempUI(FileHandler.getTexture("ui/cardDescBg"));
        descBg.basis = BasisType.BOTTOM;
        synergy = new SynergyDisplay[member.synergy.length];
        for(int i = 0; i < member.synergy.length; i++) {
            synergy[i] = new SynergyDisplay(member.synergy[i], SynergyDisplayType.card);
        }
        isCard = true;
        trackable = TrackType.CENTER;
    }

    @Override
    protected void foreUpdate() {
        clickable = isCard; 
    }

    @Override
    protected void updateButton() {
        
    }

    @Override
    protected void afterUpdate() {
        tile.update();
        cardImg.setPosition(originX, originY - originHeight * 0.1f);
        cardImg.update();
        for(int i = 0; i < synergy.length; i++) {
            SynergyDisplay s = synergy[i];
            s.setPosition(originX + originWidth * 0.4f - s.originWidth * 1.1f * (synergy.length - 1 - i), originY + originHeight * 0.45f);
            s.update();
        }
        descBg.setPosition(originX, cardImg.originY - cardImg.originHeight * 0.5f);
        descBg.update();
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if(isCard) {
            sb.draw(img, x, y, width, height);
            cardImg.render(sb);
            
            if(timer > 0) {
                descBg.img.setAlpha(timer);
                descBg.render(sb);
                descBg.img.setAlpha(1f);
                fontDesc.alpha = timer;
                FontHandler.renderMemberDesc(sb, member, fontDesc, member.desc, originX - originWidth * 0.4f, originY - originHeight * 0.3f, originWidth * 0.8f);
            }

            FontHandler.renderLineLeft(sb, fontName, member.name, originX - originWidth * 0.45f, originY + originHeight * 0.45f, originWidth);

            for(SynergyDisplay s : synergy) {
                s.render(sb);
            }
        } else {
            for(AbstractSynergy s : member.synergy) {
                SynergyDisplay sd = ScreenHandler.getInstance().battleScreen.synergies.get(s);
                if(sd.over) {
                    tile.render(sb);
                    break;
                }
            }

            member.animation.pos.set(x, y);
            member.animation.render(sb);

            //hover시 이름 출력, 폰트 투명도 = timer
            fontName.alpha = timer;
            FontHandler.renderCenter(sb, fontName, member.name, x + width * 0.9f, y + height * 0.9f);

            if(timer == 1f) {
                //효과 설명 출력
            }
        }
    }
}
