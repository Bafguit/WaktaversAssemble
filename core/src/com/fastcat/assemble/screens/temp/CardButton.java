package com.fastcat.assemble.screens.temp;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.fastcat.assemble.WaktaAssemble;
import com.fastcat.assemble.abstrcts.AbstractCard;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.actions.MoveCardToDiscardPileAction;
import com.fastcat.assemble.effects.MoeSmallCardEffect;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.InputHandler;

import static com.fastcat.assemble.abstrcts.AbstractUI.TrackType.CENTER;
import static com.fastcat.assemble.abstrcts.AbstractUI.TrackType.NONE;
import static com.fastcat.assemble.handlers.InputHandler.*;

public class CardButton extends AbstractUI {

    public BattleScreen screen;
    public AbstractCard card;
    public boolean isUsed = false;
    private float itp, acc, tar;

    public CardButton(BattleScreen screen, AbstractCard card) {
        super(FileHandler.card.get("Test"));
        this.screen = screen;
        this.card = card;
        basis = BasisType.CENTER;
        clickEnd = screen.phase == BattleScreen.BattlePhase.CARD;
        trackable = clickEnd ? CENTER : NONE;
        uiScale = 0.7f;
        itp = 0;
        acc = 0;
        tar = height * 0.4f;
    }

    @Override
    protected void updateButton() {
        overable = !isUsed && (screen.tracking == this || screen.tracking == null);
        clickEnd = screen.phase == BattleScreen.BattlePhase.CARD;
        trackable = clickEnd ? CENTER : NONE;
        tar = height * 0.4f;
    }

    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if(!isUsed && !tracking) {
                if(over) {
                    if(itp < 1) {
                        itp += WaktaAssemble.tick * 10;
                        acc = Interpolation.exp5.apply(0, tar, itp);
                        if(itp >= 1) {
                            itp = 1;
                        }
                    }
                } else {
                    if(itp > 0) {
                        itp -= WaktaAssemble.tick * 10;
                        acc = Interpolation.exp5.apply(0, tar, itp);
                        if(itp <= 0) {
                            itp = 0;
                        }
                    }
                }
                y += acc;
            }
            sb.draw(img, x, y, width, height);
        }
    }

    @Override
    protected void onClickEnd() {
        if(card.canUse() && localY > (300 * scaleY)) {
            card.use();
            screen.effectHandler.addEffect(new MoeSmallCardEffect(this, mx, my, 1800 * scaleX, 100 * scaleY));
            isUsed = true;
            screen.hand.removeValue(this, false);
        } else {
            itp = 1;
            acc = tar;
        }
    }
}
