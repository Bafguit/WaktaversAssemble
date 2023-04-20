package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.StringHandler;
import com.fastcat.assemble.screens.battle.StatusIcon;
import com.fastcat.assemble.strings.StatusString;

public abstract class AbstractStatus {

    public StatusIcon icon;
    public String id;
    public Sprite img;
    public AbstractEntity owner;
    public StatusType type;
    public StatusString.StatusData data;
    public String name;
    public String desc;
    public String[] exDesc;
    public int amount;
    public int turnLeft = -1;

    public AbstractStatus(String id, StatusType type) {
        this.img = FileHandler.status.get(id);
        data = StringHandler.statusString.get(id);
        name = data.NAME;
        desc = data.DESC;
        exDesc = data.EX_DESC;
        this.id = id;
        this.type = type;
        icon = new StatusIcon(this);
    }

    public final void apply(int amount, int turn) {
        this.amount = Math.max(this.amount, amount);
        turnLeft = Math.max(turnLeft, turn);
        onApply();
    }

    public void onInitial() {}

    protected void onApply() {}

    public void onAfterAttack() {}

    public int increaseAttack() {
        return 0;
    }

    public int addFixedAttack() {
        return 0;
    }

    public int addAttack() {
        return 0;
    }

    public int increaseDefense() {
        return 0;
    }

    public int addFixedDefense() {
        return 0;
    }

    public int addDefense() {
        return 0;
    }

    public int increaseMagicRes() {
        return 0;
    }

    public int addFixedMagicRes() {
        return 0;
    }

    public int addMagicRes() {
        return 0;
    }

    public String getDescription() {
        return desc;
    }

    public void endOfRound() {
        if(turnLeft > 0) {
            turnLeft--;
            if(turnLeft == 0) {
                remove();
            }
        }
    }

    public void remove() {
        owner.removeStatus(this);
    }

    public enum StatusType {
        BUFF, DEBUFF, CC, STATIC
    }
}
