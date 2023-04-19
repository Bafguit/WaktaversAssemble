package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractDice;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

import static com.fastcat.assemble.abstracts.AbstractUI.TrackType.CENTER;
import static com.fastcat.assemble.abstracts.AbstractUI.TrackType.NONE;
import static com.fastcat.assemble.handlers.InputHandler.*;

public class CardButton extends AbstractUI {

    public BattleScreen screen;
    public AbstractDice card;
    public boolean isUsed = false;
    private float itp, acc, tar;

    public CardButton(BattleScreen screen, AbstractDice card) {
        super(FileHandler.card.get("Test"));
        this.screen = screen;
        this.card = card;
        basis = BasisType.CENTER;
        clickEnd = screen.phase == BattleScreen.BattlePhase.SKILL;
        trackable = clickEnd ? CENTER : NONE;
        setScale(0.8f);
        itp = 0;
        acc = 0;
        tar = height * 0.35f;
        is3D = false;
    }

    @Override
    protected void updateButton() {
        overable = !isUsed;
        clickEnd = screen.phase == BattleScreen.BattlePhase.SKILL;
        trackable = clickEnd ? CENTER : NONE;
        tar = height * 0.35f;
        //tracking = tracking || isUsed;
    }

    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if(!isUsed && !tracking) {
                if(over) {
                    if(itp < 1) {
                        itp += MousseAdventure.tick * 10;
                        if(itp >= 1) {
                            itp = 1;
                        }
                        acc = Interpolation.exp5.apply(0, tar, itp);
                    }
                } else {
                    if(itp > 0) {
                        itp -= MousseAdventure.tick * 10;
                        if(itp <= 0) {
                            itp = 0;
                        }
                        acc = Interpolation.exp5.apply(0, tar, itp);
                    }
                }
                y += acc;
            }
            sb.draw(img, x, y, width, height);
            FontHandler.renderCenter(sb, FontHandler.NB30, card.name, x, y + height * 0.94f, width);
            FontHandler.renderCenterWrap(sb, FontHandler.NB26, card.desc, x + width * 0.05f,
                    y + height * 0.21f, width * 0.9f, height * 0.35f);
        }
    }

    @Override
    protected void onClickEnd() {
        if(card.canUse() && localY > (300 * scaleY)) {
            //ActionHandler.bot(new UseSkillAction(this));
            isUsed = true;
        } else {
            itp = 1;
            acc = tar;
        }
    }
}
