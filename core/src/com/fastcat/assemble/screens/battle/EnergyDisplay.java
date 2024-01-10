package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.DataHandler;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.interfaces.OnEnergyUpdated;
import com.fastcat.assemble.scene2d.SpriteAnimation;

public class EnergyDisplay extends AbstractUI implements OnEnergyUpdated {

    private final FontHandler.FontData font = FontHandler.TURN_CHANGE.cpy();

    public SpriteAnimation animation;

    public EnergyDisplay() {
        super(FileHandler.getTexture("ui/synergyIcon"));
        animation = DataHandler.getInstance().animation.get("energy");
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        sb.draw(img, x, y, width, height);
        animation.pos.set(x, y);
        animation.render(sb);
        FontHandler.renderCenter(sb, font, WakTower.game.battle.energy + "/" + WakTower.game.energyMax, x, y, width * 2);
    }

    @Override
    protected void updateButton() {
        
    }

    @Override
    public void onEnergyCharged(int amount) {
        if(amount > 0) {
            animation.setAnimation("charge");
            animation.addAnimation("idle");
        }
    }

    @Override
    public void onEnergyZero() {
        animation.setAnimation("zero");
    }
}
