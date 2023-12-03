package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractGame;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.battles.TestBattle;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.screens.battle.BattleScreen;
import com.fastcat.assemble.utils.Vector2i;

public class CardDisplay extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.TURN_CHANGE;
    private final Sprite frame;

    public AbstractMember member;
    public Vector2 originPos;

    public CardDisplay() {
        super(FileHandler.getTexture("ui/temp"));
        trackable = TrackType.CENTER;
        fluid = true;
        basis = BasisType.BOTTOM;
        frame = new Sprite(FileHandler.getTexture("ui/cardFrame"));
    }
    
    @Override
    protected void foreUpdate() {
        clickable = !ActionHandler.isRunning; 
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            sb.draw(frame, x, y);
            sb.draw(member.img, x, y, width, height);
            sb.draw(img, x, y, width, height);
            //todo 시너지 출력
            FontHandler.renderCenter(sb, font, member.getName(), x + width * 0.9f, y + height * 0.9f);
        }
    }

    @Override
    public void onClick() {
        originPos = new Vector2(originX, originY);
    }

    @Override
    public void onClickEnd() {
        setPosition(originPos.x, originPos.y);
    }
}
