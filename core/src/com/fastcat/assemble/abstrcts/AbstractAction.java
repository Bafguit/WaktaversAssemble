package com.fastcat.assemble.abstrcts;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WaktaAssemble;
import com.fastcat.assemble.handlers.SettingHandler;

public abstract class AbstractAction implements Cloneable {

    protected static final float DUR_DEFAULT = 0.5f;

    public Array<AbstractEntity> target = new Array<>();
    public AbstractCard.CardTarget tar = AbstractCard.CardTarget.NONE;
    public boolean isDone = false;
    public float baseDuration = DUR_DEFAULT;
    public float duration = DUR_DEFAULT;
    public AbstractAction preAction;

    public AbstractAction(float duration) {
        this.duration = duration;
        baseDuration = this.duration;
        applySetting();
    }

    public AbstractAction(AbstractCard.CardTarget target, float duration) {
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
                //else if (tar != AbstractCard.CardTarget.NONE) target = AbstractSkill.getTargets(actor, tar);
            updateAction();
            TickDuration();
        }
    }

    protected void applySetting() {
        /*
        if (SettingHandler.setting.fastMode) {
            baseDuration *= 0.5f;
            duration = baseDuration;
        }*/
    }

    protected abstract void updateAction();

    protected void TickDuration() {
        if (duration > 0) {
            duration -= WaktaAssemble.tick;
        }
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
