package com.fastcat.assemble.utils;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fastcat.assemble.handlers.FileHandler;

public class EnemyAction {
    public enum ActionType {
        ATTACK, BLOCK, ATTACK_BLOCK, BUFF, DEBUFF, ESCAPE
    };

    public int amount = 0;
    public int multiple = 1;
    public ActionType type;
    public TargetType target = TargetType.NONE;
    public TextureRegionDrawable image;

    public EnemyAction(ActionType type) {
        this.type = type;
        setImage();
    }

    public EnemyAction setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public EnemyAction setMultiple(int multiple) {
        this.multiple = multiple;
        return this;
    }

    public EnemyAction setTarget(TargetType target) {
        this.target = target;
        return this;
    }

    private void setImage() {
        image = new TextureRegionDrawable(FileHandler.getPng("ui/" + type.name().toLowerCase()));
    }
}
