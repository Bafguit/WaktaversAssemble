package com.fastcat.assemble.handlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Queue;
import com.fastcat.assemble.MouseAdventure;
import com.fastcat.assemble.abstrcts.AbstractAction;

public final class ActionHandler {

    public static boolean isRunning = false;
    private static ActionHandler instance;

    private final Queue<AbstractAction> actionList = new Queue<>();
    private AbstractAction current;

    public static void clear() {
        if(MouseAdventure.game != null) MouseAdventure.game.actionHandler.actionList.clear();
    }

    public static void reset() {
        if(MouseAdventure.game != null) {
            ActionHandler a = MouseAdventure.game.actionHandler;
            a.actionList.clear();
            a.current = null;
        }
    }

    public static void bot(AbstractAction action) {
        if(MouseAdventure.game != null) MouseAdventure.game.actionHandler.actionList.addLast(action);
    }

    public static void top(AbstractAction action) {
        if(MouseAdventure.game != null) MouseAdventure.game.actionHandler.actionList.addFirst(action);
    }

    public void update() {
        if (actionList.size > 0 || current != null) {
            isRunning = true;
            if (current == null) {
                current = actionList.removeFirst();
            }
            if (!MouseAdventure.fading) {
                current.update();
            }
            if (current.isDone) {
                current = null;
            }
        } else isRunning = false;
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
    }
}
