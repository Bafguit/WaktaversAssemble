package com.fastcat.assemble.screens.battle;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractScreen;
import com.fastcat.assemble.abstracts.AbstractSkill;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.handlers.InputHandler;

public class BattleScreen extends AbstractScreen {

    public PlayerDisplay player;
    public HashMap<AbstractEnemy, EnemyDisplay> enemies;
    public HashMap<AbstractMember, MemberDisplay> members;
    public HashMap<AbstractSkill, SkillDisplay> skills;
    public HashMap<AbstractSynergy, SynergyDisplay> synergies;
    public TurnEndButton turnEnd;

    public BattleScreen() {
        super(ScreenType.BASE);
        enemies = new HashMap<>();
        members = new HashMap<>();
        skills = new HashMap<>();
        synergies = new HashMap<>();
        turnEnd = new TurnEndButton();
    }

    public void initialize() {
        player = new PlayerDisplay(WakTower.game.player);
        members.clear();
        skills.clear();
        for(int i = 0; i < WakTower.game.skills.length; i++) {
            AbstractSkill s = WakTower.game.skills[i];
            skills.put(s, new SkillDisplay(s));
        }
    }

    @Override
    public void update() {
        int c = 0;
        for(MemberDisplay m : members.values()) {
            if(m.isCard) {
                c++;
                int h = WakTower.game.battle.hand.size();
                float cyl = Interpolation.exp5In.apply(0, 200, c / h);
                float cxl = Interpolation.pow2Out.apply(0, 200, c / h);
                float cy_r = Interpolation.circle.apply(cyl, 0, (c + 0.5f) / (h * 0.5f));
                float cy_l = Interpolation.circle.apply(0, cyl, (c) / (h * 0.5f));
                float cx = (cxl / (h - 1)) * c;
                float ang = Interpolation.circle.apply(0, 360, c / h);
                m.setPosition(cx * InputHandler.scaleX, (c < h / 2 ? cy_l : cy_r) * InputHandler.scaleY);
                //todo 카드 렌더
            }
            m.update();
        }
        for(SynergyDisplay s : synergies.values()) {
            s.update();
        }
        for(SkillDisplay s : skills.values()) {
            s.update();
        }
        turnEnd.update();
        player.update();
        for(EnemyDisplay e : enemies.values()) {
            e.update();
        }
    }

    @Override
    protected void render(SpriteBatch sb) {
        if(WakTower.game.battle.isPlayerTurn()) {
            for(EnemyDisplay e : enemies.values()) {
                e.render(sb);
            }
            player.render(sb);
        } else {
            player.render(sb);
            for(EnemyDisplay e : enemies.values()) {
                e.render(sb);
            }
        }

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
