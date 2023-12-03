package com.fastcat.assemble.screens.battle;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractScreen;
import com.fastcat.assemble.abstracts.AbstractSkill;

public class BattleScreen extends AbstractScreen {

    public PlayerDisplay player;
    public HashMap<AbstractEnemy, EnemyDisplay> enemies;
    public HashMap<AbstractMember, CardDisplay> cards;
    public HashMap<AbstractSkill, SkillDisplay> skills;
    public MemberDisplay[] members;
    public TurnEndButton turnEnd;

    public BattleScreen() {
        super(ScreenType.BASE);
        enemies = new HashMap<>();
        cards = new HashMap<>();
        skills = new HashMap<>();
        members = new MemberDisplay[WakTower.game.memberLimit];
        turnEnd = new TurnEndButton();
    }

    @Override
    public void update() {
        
    }

    @Override
    protected void render(SpriteBatch sb) {
        if(WakTower.game.battle.isPlayerTurn()) {
            player.render(sb);
            for(EnemyDisplay e : enemies.values()) {
                e.render(sb);
            }
        } else {
            for(EnemyDisplay e : enemies.values()) {
                e.render(sb);
            }
            player.render(sb);
        }

        for(MemberDisplay m : members) {
            m.render(sb);
        }

        effectHandler.render(sb);

        turnEnd.render(sb);

        for(SkillDisplay s : skills.values()) {
            s.render(sb);
        }

        for(CardDisplay c : cards.values()) {
            c.render(sb);
        }
    }
}
