package com.fastcat.assemble.screens.battle;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.interfaces.OnStatusUpdated;
import com.fastcat.assemble.utils.HealthBar;

public class EnemyDisplay extends AbstractUI implements OnStatusUpdated {

    public AbstractEnemy enemy;
    public LinkedList<StatusDisplay> status;
    public HealthBar healthBar;

    public EnemyDisplay(AbstractEnemy e) {
        super(FileHandler.getTexture("ui/tile"));
        status = new LinkedList<>();
        enemy = e;
        enemy.statusUpdatedListener.add(this);
        basis = BasisType.BOTTOM;
        healthBar = new HealthBar(enemy);
        enemy.animation.setAnimation("idle");
        enemy.animation.setScale(0.35f);
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            enemy.animation.pos.set(localX, localY);
            //enemy.animation.render(sb);

            healthBar.render(sb);

            for(int i = 0; i < status.size(); i++) {
                StatusDisplay s = status.get(i);
                s.render(sb);
            }

            if(timer >= 1f) {
                //효과 설명 출력
            }
        }
    }

    @Override
    protected void updateButton() {
        for(int i = 0; i < status.size(); i++) {
            StatusDisplay s = status.get(i);
            s.setPosition(originX - originWidth * 0.5f + s.originWidth * i, originY - originHeight * 0.5f - s.originHeight);
            s.update();
        }
        healthBar.update();
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        healthBar.setPosition(x, y);
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
    }

    @Override
    public void onStatusInitial(AbstractStatus status) {
        this.status.add(new StatusDisplay(status));
    }
}
