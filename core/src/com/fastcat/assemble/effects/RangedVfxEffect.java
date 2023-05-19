package com.fastcat.assemble.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractEffect;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.handlers.FileHandler;

public class RangedVfxEffect extends AbstractEffect {

    public AbstractEntity source;
    public AbstractEntity target;
    private RangedVfx vfx;
    private AbstractUI.TempUI sprite;
    private AbstractEntity.DamageInfo info;
    private float speed;
    private float time = 0;
    private float xd, yd;
    private float fromX, fromY, toX, toY;

    public RangedVfxEffect(AbstractEntity target, AbstractEntity source, AbstractEntity.DamageInfo info, RangedVfx vfx, boolean fast) {
        super(2f);
        this.target = target;
        this.source = source;
        this.vfx = vfx;
        this.info = info;
        speed = fast ? 800 : 400;
        fromX = source.pos.x + source.attackFrom.x;
        fromY = source.pos.y + source.attackFrom.y;
        toX = target.pos.x;
        toY = target.pos.y;
        xd = fromX - toX;
        yd = fromY - toY;
        float dist = (float) Math.sqrt(xd * xd + yd * yd);
        baseDuration = duration = dist / speed;
        sprite = new AbstractUI.TempUI(FileHandler.vfx.get(vfx.toString()));
    }

    @Override
    protected void renderEffect(SpriteBatch sb) {
        float sp = 1 - duration / baseDuration;
        float ax = Interpolation.linear.apply(fromX, toX, sp);
        float ay = Interpolation.linear.apply(fromY, toY, sp);
        sprite.setPosition(ax, ay);
        sprite.update();
        sprite.render(sb);

        if(isDone) {
            Array<AbstractEntity> e = new Array<>();
            e.add(target);
            source.attack(e, info);
        }
    }

    public enum RangedVfx {
        ARTS_1
    }
}
