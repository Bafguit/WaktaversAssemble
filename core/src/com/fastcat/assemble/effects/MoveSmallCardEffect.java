package com.fastcat.assemble.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.fastcat.assemble.abstrcts.AbstractEffect;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.utils.FastCatUtils;

public class MoveSmallCardEffect extends AbstractEffect {

    private final AbstractUI ui;
    private final float fx, fy, tx, ty, angle;

    public MoveSmallCardEffect(AbstractUI card, float fromX, float fromY, float toX, float toY) {
        super(fromX, fromY, 0.4f);
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
    protected void renderEffect(SpriteBatch sb) {
        if(!isDone) {
            float current = (baseDuration - duration) / baseDuration;
            x = Interpolation.pow2InInverse.apply(fx, tx, current);
            y = Interpolation.linear.apply(fy, ty, current);

            ui.img.setBounds(x, y, ui.width, ui.height);
            ui.img.setOriginCenter();
            ui.img.setRotation(Interpolation.exp10Out.apply(0, angle, current));
            ui.img.draw(sb);
        }
    }
}
