package com.fastcat.assemble.handlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Queue;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractAction;
import com.fastcat.assemble.abstrcts.AbstractEffect;

public final class ActionHandler {

    public static boolean isRunning = false;
    private static final EffectHandler effectHandler = new EffectHandler();

    private final Queue<AbstractAction> actionList = new Queue<>();
    private AbstractAction current;

    public static void clear() {
        if(MousseAdventure.game != null) MousseAdventure.game.actionHandler.actionList.clear();
    }

    public static void reset() {
        if(MousseAdventure.game != null) {
            ActionHandler a = MousseAdventure.game.actionHandler;
            a.actionList.clear();
            a.current = null;
        }
    }

    public static void add(AbstractEffect effect) {
        effectHandler.addEffect(effect);
    }

    public static void bot(AbstractAction action) {
        if(MousseAdventure.game != null) MousseAdventure.game.actionHandler.actionList.addLast(action);
    }

    public static void top(AbstractAction action) {
        if(MousseAdventure.game != null) MousseAdventure.game.actionHandler.actionList.addFirst(action);
    }

    public void update() {
        isRunning = false;
        if (actionList.size > 0 || current != null) {
            isRunning = true;
            if (current == null) {
                current = actionList.removeFirst();
            }
            if (!MousseAdventure.fading) {
                current.update();
            }
            if (current.isDone) {
                current = null;
            }
        }

        if(effectHandler.effectList.size > 0) {
            isRunning = true;
        }
    }

    public void render(SpriteBatch sb) {
        if(current != null) {
            current.render(sb);
        }
        if(actionList.size > 0) {
            for(AbstractAction a : actionList) {
                a.render(sb);
            }
        }
        if(effectHandler.effectList.size > 0) {
            effectHandler.render(sb);
        }
    }
}
