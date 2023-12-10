package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class MemberDisplay extends AbstractUI {

    private final FontHandler.FontData fontName = FontHandler.SUB_DESC.cpy();
    private final FontHandler.FontData fontDesc = FontHandler.SUB_DESC.cpy();
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
        //tile 206x87
        tile = new AbstractUI.TempUI(FileHandler.getTexture("ui/memberTile"));
        //frame 206x279
        frame = new Sprite(FileHandler.getTexture("ui/cardFrame"));
        cardImg = new AbstractUI.TempUI(member.img.getTexture());
        //descBg 높이: 160
        descBg = new AbstractUI.TempUI(FileHandler.getTexture("ui/cardDesc"));
        descBg.basis = BasisType.BOTTOM;
        synergy = new SynergyDisplay[member.synergy.length];
        for(int i = 0; i < member.synergy.length; i++) {
            synergy[i] = new SynergyDisplay(member.synergy[i], SynergyDisplayType.card);
        }
        isCard = true;
        trackable = TrackType.CENTER;
        fluid = true;
    }

    @Override
    protected void foreUpdate() {
        clickable = isCard; 
    }

    @Override
    protected void updateButton() {
        if(over) InputHandler.alreadyOver = true;
        cardImg.setPosition(originX, originY - originHeight * 0.5f + 14 + cardImg.originHeight * 0.5f);
    }

    @Override
    protected void afterUpdate() {
        tile.update();
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
            float yy = y - height * 0.5f + cardImg.height * 0.5f + 14 * InputHandler.scaleA;
            sb.draw(cardImg.img, cardImg.x, cardImg.y, cardImg.width, cardImg.height);
            sb.draw(frame, cardImg.x, cardImg.y, cardImg.width, cardImg.height);
            
            if(timer > 0) {
                descBg.img.setAlpha(timer);
                descBg.render(sb);
                descBg.img.setAlpha(1f);
                fontDesc.alpha = timer;
                FontHandler.renderMemberDesc(sb, member, fontDesc, member.desc, x + width * 0.1f, y + height * 0.2f, width * 0.8f);
            }

            FontHandler.renderMemberName(sb, fontName, member.name, x + width * 0.08f, y + height * 0.92f, width);

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

            fontName.alpha = timer;
            FontHandler.renderCenter(sb, fontName, member.name, x + width * 0.9f, y + height * 0.9f);

            if(timer == 1f) {
                //효과 설명 출력
            }
        }
    }

    @Override
    protected void onClickEnd() {
        if(y > height) {
            ActionHandler.bot(new SummonMemberAction(member));
        }
    }
}
