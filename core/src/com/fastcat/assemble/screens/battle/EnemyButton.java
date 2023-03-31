package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstrcts.AbstractEntity;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.enemies.TestEnemy;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.utils.Vector2i;

import java.util.Queue;

public class EnemyButton extends AbstractUI {

    private final Array<SubText> sub = new Array<>();
    private Queue<Vector2i> path;

    public BattleScreen screen;
    public AbstractEntity entity;
    public TileSquare tile;

    public EnemyButton(BattleScreen screen) {
        this(screen, new TestEnemy());
    }

    public EnemyButton(BattleScreen screen, AbstractEntity dice) {
        super(FileHandler.character.get("Test"));
        pix();
        this.entity = dice;
        this.screen = screen;
        clickable = false;
        sub.add(new SubText(dice.name, dice.desc));
    }

    @Override
    protected void updateButton() {
        if(tile != null) {
            entity.pos.set(originX, originY);
        }
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled && entity != null) {
            if (showImg) sb.draw(entity.img, x, y, width, height);
        }
    }

    public void setPath(Queue<Vector2i> v) {
        path = v;
    }

    public Vector2i getNextPath() {
        return path.element();
    }

    public Vector2i nextPath() {
        return path.poll();
    }
}
