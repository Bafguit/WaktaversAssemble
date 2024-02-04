package com.fastcat.assemble.stages.battle;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.abstracts.AbstractPlayer;
import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.interfaces.OnStatusUpdated;
import com.fastcat.assemble.utils.HealthBar;

public class EnemyDisplay extends Table implements OnStatusUpdated {

    private Table statusTable;

    public AbstractEnemy enemy;
    public LinkedList<StatusDisplay> status;
    public HealthBar healthBar;

    public EnemyDisplay(AbstractEnemy enemy) {
        status = new LinkedList<>();
        this.enemy = enemy;
        this.enemy.statusUpdatedListener.add(this);
        healthBar = new HealthBar(this.enemy);
        this.enemy.animation.setAnimation("idle");
        statusTable = new Table();
        statusTable.align(Align.topLeft);
        updateStatus();
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        healthBar.setPosition(x, y);
    }

    private void updateStatus() {
        statusTable.clearChildren();

        int i = 0;
        for(StatusDisplay s : status) {
            statusTable.add(s);
            i++;
            if(i == 5) {
                statusTable.row().top().left();
                i = 0;
            }
        }
    }

    @Override
    public void onStatusRemoved(AbstractStatus status) {
        Iterator<StatusDisplay> itr = this.status.iterator();
        while(itr.hasNext()) {
            StatusDisplay s = itr.next();
            if(s.status == status) {
                itr.remove();
                break;
            }
        }
        updateStatus();
    }

    @Override
    public void onStatusInitial(AbstractStatus status) {
        this.status.add(new StatusDisplay(status));
        updateStatus();
    }
}
