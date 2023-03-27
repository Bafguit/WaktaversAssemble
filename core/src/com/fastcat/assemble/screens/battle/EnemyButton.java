package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MouseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.enemies.TestEnemy;
import com.fastcat.assemble.handlers.FileHandler;

public class EnemyButton extends AbstractUI {

    private final Array<SubText> sub = new Array<>();

    public BattleScreen screen;
    public AbstractEntity entity;
    public TileSquare tile;

    public EnemyButton(BattleScreen screen) {
        this(screen, MouseAdventure.game.player);
    }

    public EnemyButton(BattleScreen screen, AbstractEntity dice) {
        super(FileHandler.character.get("Test"));
        pix();
        this.entity = dice;
        this.screen = screen;
        clickable = false;
        sub.add(new SubText(dice.desc));
        entity = new TestEnemy();
    }

    @Override
    protected void updateButton() {

    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled && entity != null) {
            if (showImg) sb.draw(entity.img, x, y, width, height);
        }
    }
}
