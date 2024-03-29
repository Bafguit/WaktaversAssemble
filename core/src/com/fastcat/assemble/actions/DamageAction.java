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
        super(target, isFast ? 0.15f : 0.5f);
        this.info = info;
    }

    // Several
    public DamageAction(DamageInfo info, Array<AbstractEntity> target, boolean isFast) {
        super(target, isFast ? 0.15f : 0.5f);
        this.info = info;
    }

    // Target Type
    public DamageAction(DamageInfo info, TargetType target, boolean isFast) {
        super(target, isFast ? 0.15f : 0.5f);
        this.info = info;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            if(info.member != null) info.damage = info.member.tempClone.calculatedAtk();
            else if(info.source != null && info.source instanceof AbstractEnemy) {
                info.damage = ((AbstractEnemy)info.source).calculateDamage(info.damage);
                if(target.size == 0) {
                    //TODO 카드 타는거 만들기: 대미지 받은 만큼 손 -> 뽑을 카드 -> 버린 카드 순으로 태우기, 다 태우면 패배
                }
            }
            if(target.size > 0) {
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
}
