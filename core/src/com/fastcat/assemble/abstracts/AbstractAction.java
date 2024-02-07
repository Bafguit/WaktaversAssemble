package com.fastcat.assemble.abstracts;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.utils.TargetType;

public abstract class AbstractAction implements Cloneable {

    protected static final float DUR_DEFAULT = 0.5f;

    private AbstractSynergy synergyTarget;

    public AbstractEntity source;
    public Array<AbstractEntity> target = new Array<>();
    public TargetType tar = TargetType.NONE;
    public boolean isDone = false;
    public boolean run = true;
    public float baseDuration = DUR_DEFAULT;
    public float duration = DUR_DEFAULT;
    public int amount;
    public AbstractAction preAction;

    public AbstractAction(float duration) {
        this.duration = duration;
        baseDuration = this.duration;
        applySetting();
    }

    public AbstractAction(TargetType target, float duration) {
        tar = target;
        this.duration = duration;
        baseDuration = this.duration;
        applySetting();
    }

    public AbstractAction(Array<AbstractEntity> target, float duration) {
        this.target = target;
        this.duration = duration;
        baseDuration = this.duration;
        applySetting();
    }

    public AbstractAction(AbstractEntity target, float duration) {
        this.target = new Array<>();
        this.target.add(target);
        this.duration = duration;
        baseDuration = this.duration;
        applySetting();
    }

    public final void update() {
        if (!isDone) {
            if (duration <= 0) isDone = true;
            else if (duration == baseDuration)
                if (preAction != null) target = preAction.target;
                else if(synergyTarget != null) {
                    for(AbstractEntity e : synergyTarget.members) {
                        if(e.isAlive()) target.add(e);
                    }
                } else if (tar != TargetType.NONE) target = tar.getTargets(source);
            updateAction();
            if(run) TickDuration();
        }
    }

    public final void render(SpriteBatch sb) {
        renderAction(sb);
    }

    public final AbstractAction setSynergy(AbstractSynergy s) {
        synergyTarget = s;
        return this;
    }

    protected void applySetting() {
        /*
        if (SettingHandler.setting.fastMode) {
            baseDuration *= 0.5f;
            duration = baseDuration;
        }*/
    }

    protected abstract void updateAction();

    protected void renderAction(SpriteBatch sb) {}

    protected void TickDuration() {
        if (duration > 0) {
            duration -= WakTower.tick;
        }
    }

    protected void addToBot(AbstractAction action) {
        ActionHandler.bot(action);
    }

    protected void addToTop(AbstractAction action) {
        ActionHandler.top(action);
    }

    protected void addToNext(AbstractAction action) {
        ActionHandler.next(action);
    }

    @Override
    public AbstractAction clone() {
        try {
            return (AbstractAction) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
