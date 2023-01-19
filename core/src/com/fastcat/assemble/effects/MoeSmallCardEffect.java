package com.fastcat.assemble.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.fastcat.assemble.abstrcts.AbstractCard;
import com.fastcat.assemble.abstrcts.AbstractEffect;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.screens.temp.CardButton;
import com.fastcat.assemble.utils.FastCatUtils;

import static com.fastcat.assemble.handlers.InputHandler.*;

public class MoeSmallCardEffect extends AbstractEffect {

    private final AbstractCard card;
    private final CardButton ui;
    private float fx, fy, tx, ty, angle;

    public MoeSmallCardEffect(CardButton card, float fromX, float fromY, float toX, float toY) {
        super(fromX, fromY, 0.4f);
        this.card = card.card;
        ui = card;
        ui.basis = AbstractUI.BasisType.CENTER;
        ui.setScale(0.3f);
        fx = x;
        fy = y;
        tx = toX;
        ty = toY;
        angle = FastCatUtils.getAngle(fx, fy, tx, ty);
    }

    @Override
    protected void updateEffect() {
        if(!isDone) {
            if (card != null) {
                float current = (baseDuration - duration) / baseDuration;
                x = Interpolation.pow2InInverse.apply(fx, tx, current);
                y = Interpolation.linear.apply(fy, ty, current);

                ui.img.setBounds(x, y, ui.width, ui.height);
                ui.img.setOriginCenter();
                ui.img.setRotation(Interpolation.exp10Out.apply(0, angle, current));
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        ui.img.draw(sb);
    }
}
