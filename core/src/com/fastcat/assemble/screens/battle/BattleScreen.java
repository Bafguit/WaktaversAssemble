package com.fastcat.assemble.screens.battle;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractScreen;
import com.fastcat.assemble.abstracts.AbstractSkill;
import com.fastcat.assemble.abstracts.AbstractSynergy;

public class BattleScreen extends AbstractScreen {

    public PlayerDisplay player;
    public HashMap<AbstractEnemy, EnemyDisplay> enemies;
    public HashMap<AbstractMember, MemberDisplay> hand;
    public HashMap<AbstractMember, MemberDisplay> members;
    public HashMap<AbstractSkill, SkillDisplay> skills;
    public HashMap<AbstractSynergy, SynergyDisplay> synergies;
    public TurnEndButton turnEnd;

    public BattleScreen() {
        super(ScreenType.BASE);
        enemies = new HashMap<>();
        hand = new HashMap<>();
        members = new HashMap<>();
        skills = new HashMap<>();
        synergies = new HashMap<>();
        turnEnd = new TurnEndButton();
        initialize();
    }

    public void initialize() {
        player = new PlayerDisplay(WakTower.game.player);
        hand.clear();
        members.clear();
        skills.clear();
        for(int i = 0; i < WakTower.game.skills.length; i++) {
            AbstractSkill s = WakTower.game.skills[i];
            skills.put(s, new SkillDisplay(s));
        }
    }

    @Override
    public void update() {
        int c = 0, hs = hand.size();
        float hw = (110 * hs + 118) * 0.5f;
        for(MemberDisplay h : hand.values()) {
            h.fluid = true;
            if(h.over) {
                h.setPosition(hw - c * 110, 171);
                h.update();
            } else h.setPosition(hw - c * 110, 71);
        }
        for(MemberDisplay h : hand.values()) {
            if(!h.over) h.update();
        }

        for(MemberDisplay m : members.values()) {
            m.update();
        }
        for(SynergyDisplay s : synergies.values()) {
            s.update();
        }
        /*for(SkillDisplay s : skills.values()) {
            s.update();
        }*/
        turnEnd.update();
        player.update();
        for(EnemyDisplay e : enemies.values()) {
            e.update();
        }
    }

    public void addHand(AbstractMember member) {
        hand.put(member, new MemberDisplay(member));
    }

    @Override
    protected void render(SpriteBatch sb) {
        /*if(WakTower.game.battle.isPlayerTurn()) {
            for(EnemyDisplay e : enemies.values()) {
                e.render(sb);
            }
            player.render(sb);
        } else {
            player.render(sb);
            for(EnemyDisplay e : enemies.values()) {
                e.render(sb);
            }
        }*/

        for(MemberDisplay m : members.values()) {
            if(!m.isCard) m.render(sb);
        }

        effectHandler.render(sb);

        turnEnd.render(sb);

        for(SkillDisplay s : skills.values()) {
            s.render(sb);
        }

        for(MemberDisplay m : members.values()) {
            if(m.isCard) m.render(sb);
        }
    }
}
