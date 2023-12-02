package com.fastcat.assemble.screens.mainmenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractChar;
import com.fastcat.assemble.abstracts.AbstractGame;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.battles.TestBattle;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.screens.battle.BattleScreen;
import com.fastcat.assemble.utils.Vector2i;

import static com.fastcat.assemble.WakTower.battleScreen;

public class CardDisplay extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.TURN_CHANGE;
    private final Sprite tile;

    public AbstractChar member;
    private float timer = 0f;

    public CardDisplay() {
        super(FileHandler.getTexture("ui/temp"));
        clickable = false;
        basis = BasisType.BOTTOM;
        tile = new Sprite(FileHandler.getTexture("ui/memberTile"));
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (member != null) {
            //시너지 hover일 때 출력
            if(true) {
                sb.draw(img, x, y, width, height);
            }

            member.animation.pos.set(x, y);
            member.animation.render(sb);

            //hover시 이름 출력, 폰트 투명도 = timer
            FontHandler.renderCenter(sb, font, member.name, x + width * 0.9f, y + height * 0.9f);

            if(timer >= 1f) {
                //효과 설명 출력
            }
        }
    }

    @Override
    protected void updateButton() {
        if(over) {
            if(timer < 1f) {
                timer += WakTower.tick / 0.5f;
            }
        } else timer = 0f;
    }

    public void setMember(AbstractChar member) {
        this.member = null;
        this.member = member;
    }
}
