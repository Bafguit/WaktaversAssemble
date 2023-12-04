package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.screens.battle.SynergyDisplay.SynergyDisplayType;

public class MemberDisplay extends AbstractUI {

    private final FontHandler.FontData fontName = FontHandler.TURN_CHANGE.cpy();
    private final FontHandler.FontData fontDesc = FontHandler.TURN_CHANGE.cpy();
    private final AbstractUI.TempUI cardImg, tile, descBg; 
    private final SynergyDisplay[] synergy;
    private final Sprite frame;
    private float timer = 0f;

    public final AbstractMember member;
    public boolean isCard;

    public MemberDisplay(AbstractMember member) {
        super(FileHandler.getTexture("ui/cardBg"));
        this.member = member;
        clickable = true;
        basis = BasisType.CENTER;
        tile = new AbstractUI.TempUI(FileHandler.getTexture("ui/memberTile"));
        frame = new Sprite(FileHandler.getTexture("ui/cardFrame"));
        cardImg = new AbstractUI.TempUI(member.img.getTexture());
        descBg = new AbstractUI.TempUI(FileHandler.getTexture("ui/descBg"));
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
        if(over) {
            if(timer < 1f) {
                timer += WakTower.tick / 0.5f;
                if(timer >= 1f) timer = 1f;
            }
        } else timer = 0f;
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

            FontHandler.renderLineLeft(sb, fontName, member.name, originX - originWidth * 0.45f, originY + originHeight * 0.45f, originWidth);
            
            if(timer > 0) {
                FontHandler.renderMemberDesc(sb, member, fontDesc, member.desc, originX - originWidth * 0.4f, originY - originHeight * 0.3f, originWidth * 0.8f);
                descBg.render(sb);
            }

            for(SynergyDisplay s : synergy) {
                s.render(sb);
            }
        } else {
            //시너지 hover일 때 출력
            if(true) {
                sb.draw(tile.img, x, y, tile.width, tile.height);
            }

            member.animation.pos.set(x, y);
            member.animation.render(sb);

            //hover시 이름 출력, 폰트 투명도 = timer
            FontHandler.renderCenter(sb, fontName, member.name, x + width * 0.9f, y + height * 0.9f);

            if(timer == 1f) {
                //효과 설명 출력
            }
        }
    }
}
