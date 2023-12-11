package com.fastcat.assemble.screens.battle;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.interfaces.OnStatusUpdated;

public class EnemyDisplay extends AbstractUI implements OnStatusUpdated {

    private final FontHandler.FontData font = FontHandler.TURN_CHANGE.cpy();

    public AbstractEnemy enemy;
    public LinkedList<StatusDisplay> status;

    public EnemyDisplay() {
        super(FileHandler.getTexture("ui/entityBlank"));
        enemy.statusUpdatedListener.add(this);
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            enemy.animation.pos.set(x, y);
            enemy.animation.render(sb);

            renderHealthBar(sb);

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
            s.update();
        }
    }

    private void renderHealthBar(SpriteBatch sb) {

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
