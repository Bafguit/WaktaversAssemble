package com.fastcat.assemble.abstrcts;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WaktaAssemble;
import com.fastcat.assemble.handlers.SettingHandler;

public abstract class AbstractAction implements Cloneable {
/*
    protected static final float DUR_DEFAULT = 0.5f;

    public AbstractEntity actor;
    public Array<AbstractEntity> target = new Array<>();
    public AbstractSkill.SkillTarget tar = AbstractSkill.SkillTarget.NONE;
    public boolean isDone = false;
    public float baseDuration = DUR_DEFAULT;
    public float duration = DUR_DEFAULT;
    public AbstractAction preAction;

    public AbstractAction(AbstractEntity actor, float duration) {
        this.actor = actor;
        this.duration = duration;
        baseDuration = this.duration;
        applySetting();
    }

    public AbstractAction(AbstractEntity actor, AbstractSkill.SkillTarget target, float duration) {
        this.actor = actor;
        tar = target;
        this.duration = duration;
        baseDuration = this.duration;
        applySetting();
    }

    public AbstractAction(AbstractEntity actor, Array<AbstractEntity> target, float duration) {
        this.actor = actor;
        this.target = target;
        this.duration = duration;
        baseDuration = this.duration;
        applySetting();
    }

    public AbstractAction(AbstractEntity actor, AbstractEntity target, float duration) {
        this.actor = actor;
        this.target = new Array<>();
        this.target.add(target);
        this.duration = duration;
        baseDuration = this.duration;
        applySetting();
    }

    public final void update() {
        if (!isDone) {
            if (actor != null && !actor.isAlive()) {
                isDone = true;
                return;
            } else if (duration <= 0) isDone = true;
            else if (duration == baseDuration)
                if (preAction != null) target = preAction.target;
                else if (tar != AbstractSkill.SkillTarget.NONE) target = AbstractSkill.getTargets(actor, tar);
            updateAction();
            TickDuration();
        }
    }

    protected void applySetting() {
        if (SettingHandler.setting.fastMode) {
            baseDuration *= 0.5f;
            duration = baseDuration;
        }
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
    }*/
}
