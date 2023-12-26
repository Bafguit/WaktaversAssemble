package com.fastcat.assemble.screens.battle;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.abstracts.AbstractPlayer;
import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.interfaces.OnStatusUpdated;
import com.fastcat.assemble.utils.HealthBar;

public class PlayerDisplay extends AbstractUI implements OnStatusUpdated {

    private final FontHandler.FontData font = FontHandler.TURN_CHANGE.cpy();

    public AbstractPlayer player;
    public LinkedList<StatusDisplay> status;
    public HealthBar healthBar;

    public PlayerDisplay(AbstractPlayer player) {
        super(FileHandler.getTexture("ui/tile"));
        status = new LinkedList<>();
        this.player = player;
        this.player.statusUpdatedListener.add(this);
        basis = BasisType.BOTTOM;
        healthBar = new HealthBar(this.player);
        this.player.animation.setAnimation("idle");
        this.player.animation.setScale(0.25f);
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        player.animation.pos.set(localX, localY);
        player.animation.render(sb);
        healthBar.render(sb);
        
        for(int i = 0; i < status.size(); i++) {
            StatusDisplay s = status.get(i);
            s.render(sb);
        }

        if(timer >= 1f) {
            //효과 설명 출력
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
