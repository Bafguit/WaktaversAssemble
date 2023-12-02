package com.fastcat.assemble.screens.mainmenu;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractScreen;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.InputHandler;

public class BattleScreen extends AbstractScreen {

    public PlayerDisplay player;
    public HashMap<AbstractEnemy, EnemyDisplay> enemies;
    public HashMap<AbstractChar, CardDisplay> cards;
    public MemberDisplay[] members;
    public TurnEndButton turnEnd;

    public BattleScreen() {
        super(ScreenType.BASE);
    }

    @Override
    public void update() {
        
    }

    @Override
    protected void render(SpriteBatch sb) {
        
    }
}
