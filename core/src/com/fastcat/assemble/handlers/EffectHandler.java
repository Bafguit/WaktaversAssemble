package com.fastcat.assemble.handlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Queue;
import com.fastcat.assemble.WaktaAssemble;
import com.fastcat.assemble.abstrcts.AbstractEffect;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Iterator;

public final class EffectHandler {

    public Queue<AbstractEffect> effectList = new Queue<>();

    public boolean isShaking = false;

    public static void add(AbstractEffect e) {
        WaktaAssemble.application.screen.getEffectHandler().effectList.addLast(e);
    }

    public void addEffect(AbstractEffect e) {
        effectList.addLast(e);
    }

    public void render(SpriteBatch sb) {
        if (effectList.size > 0) {
            Iterator<AbstractEffect> it = effectList.iterator();
            while (it.hasNext()) {
                AbstractEffect e = it.next();
                e.render(sb);
                if (e.isDone) {
                    e.onRemove();
                    e.dispose();
                    it.remove();
                }
            }
        }
    }

    public void removeAll() {
        for (AbstractEffect e : effectList) {
            e.onRemove();
            e.dispose();
        }
        effectList.clear();
    }
}
