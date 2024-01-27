package com.fastcat.assemble.actions;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.TargetType;

public class DamageAction extends AbstractAction {

    public DamageInfo info;
    public AbstractMember member;

    // Specific
    public DamageAction(DamageInfo info, AbstractEntity target) {
        super(target, 0.5f);
        this.info = info;
    }

    // Several
    public DamageAction(DamageInfo info, Array<AbstractEntity> target) {
        super(target, 0.5f);
        this.info = info;
    }

    // Target Type
    public DamageAction(DamageInfo info, TargetType target) {
        super(target, 0.5f);
        this.info = info;
    }

    // Specific
    public DamageAction(DamageInfo info, AbstractEntity target, boolean isFast) {
        super(target, isFast ? 0.2f : 0.5f);
        this.info = info;
    }

    // Several
    public DamageAction(DamageInfo info, Array<AbstractEntity> target, boolean isFast) {
        super(target, isFast ? 0.2f : 0.5f);
        this.info = info;
    }

    // Target Type
    public DamageAction(DamageInfo info, TargetType target, boolean isFast) {
        super(target, isFast ? 0.2f : 0.5f);
        this.info = info;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(info.member != null) info.damage = info.member.calculatedAtk();
            if(target.size == 0) {
                AbstractEntity ent = WakTower.game.player;
                if(info.source == null || info.source.isPlayer) {
                    Array<AbstractEntity> entities = new Array<>();
                    for(AbstractEnemy e : WakTower.game.battle.enemies) {
                        if(e.isAlive()) entities.add(e);
                    }
                    int r = WakTower.game.battleRandom.random(entities.size - 1);
                    ent = entities.get(r);
                }
                target.add(ent);
            }

            for(AbstractRelic item : WakTower.game.relics) {
                item.onAttack(info, target);
            }
            for(AbstractMember c : WakTower.game.battle.members) {
                c.onAttack(info, target);
            }
            if(info.source != null) {
                for(AbstractStatus s : info.source.status) {
                    s.onAttack(info, target);
                }
            }

            for(AbstractEntity e : target) {
                e.takeDamage(info.cpy());
            }
        }
    }
}
