package com.fastcat.assemble.screens.battle;

import org.jetbrains.annotations.ApiStatus.OverrideOnly;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

public class MemberDisplay extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.TURN_CHANGE;
    private final Sprite frame, tile;

    public boolean isCard;
    public AbstractMember member;
    private float timer = 0f;

    public MemberDisplay(AbstractMember member) {
        super(FileHandler.getTexture("ui/temp"));
        this.member = member;
        clickable = true;
        basis = BasisType.BOTTOM;
        tile = new Sprite(FileHandler.getTexture("ui/memberTile"));
        frame = new Sprite(FileHandler.getTexture("ui/cardFrame"));
        isCard = true;
    }

    @Override
    protected void foreUpdate() {
        clickable = isCard; 
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if(isCard) {
            
        } else {
            //시너지 hover일 때 출력
            if(true) {
                sb.draw(img, x, y, width, height);
            }

            member.animation.pos.set(x, y);
            member.animation.render(sb);

            //hover시 이름 출력, 폰트 투명도 = timer
            FontHandler.renderCenter(sb, font, member.name, x + width * 0.9f, y + height * 0.9f);

            if(timer == 1f) {
                //효과 설명 출력
            }
        }
    }

    @Override
    protected void updateButton() {
        if(member != null) {
            if(over) {
                if(timer < 1f) {
                    timer += WakTower.tick / 0.5f;
                    if(timer >= 1f) timer = 1f;
                }
            } else timer = 0f;
        }
    }
}
