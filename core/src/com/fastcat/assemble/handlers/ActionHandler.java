package com.fastcat.assemble.handlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Queue;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractEffect;

import static com.fastcat.assemble.WakTower.game;

public final class ActionHandler {

    public static boolean isRunning = false;
    private static final EffectHandler effectHandler = new EffectHandler();

    private final Queue<AbstractAction> actionList = new Queue<>();
    private final Queue<AbstractAction> nextActions = new Queue<>();
    private final Queue<AbstractAction> setActions = new Queue<>();
    private AbstractAction current;

    public static void clear() {
        if(game != null) {
            game.actionHandler.actionList.clear();
            game.actionHandler.nextActions.clear();
            game.actionHandler.setActions.clear();
        }
    }

    public static void reset() {
        if(game != null) {
            ActionHandler a = game.actionHandler;
            a.actionList.clear();
            a.nextActions.clear();
            a.setActions.clear();
            a.current = null;
        }
    }

    public static void add(AbstractEffect effect) {
        effectHandler.addEffect(effect);
    }

    public static void set(AbstractAction action) {
        if(game != null) {
            if(game.actionHandler.current != null) {
                game.actionHandler.setActions.addLast(action);
            } else {
                game.actionHandler.actionList.addLast(action);
            }
        }
    }

    public static void setTop(AbstractAction action) {
        if(game != null) {
            if(game.actionHandler.current != null) {
                game.actionHandler.setActions.addFirst(action);
            } else {
                game.actionHandler.actionList.addFirst(action);
            }
        }
    }

    public static void next(AbstractAction action) {
        if(game != null) {
            if(game.actionHandler.current != null) {
                game.actionHandler.nextActions.addLast(action);
            } else {
                game.actionHandler.actionList.addLast(action);
            }
        }
    }

    public static void bot(AbstractAction action) {
        if(game != null) game.actionHandler.actionList.addLast(action);
    }

    public static void top(AbstractAction action) {
        if(game != null) game.actionHandler.actionList.addFirst(action);
    }

    public void update() {
        isRunning = false;
        do {
            if (current != null) {
                isRunning = true;
                if (!WakTower.fading) {
                    current.update();
                }
                if(current.isDone) {
                    current = null;
                    if (setActions.size > 0) {
                        current = setActions.removeFirst();
                    } else if (nextActions.size > 0) {
                        current = nextActions.removeFirst();
                    } else if (actionList.size > 0) {
                        current = actionList.removeFirst();
                    }
                }
            } else if(actionList.size > 0) {
                current = actionList.removeFirst();
                if(!WakTower.fading) {
                    current.update();
                }
            }
        } while(current != null && current.baseDuration == 0);

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

    public static EffectHandler getEffectHandler() {
        return effectHandler;
    }
}
