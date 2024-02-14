package com.fastcat.assemble.stages.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.uis.SpriteAnimation;
import com.fastcat.assemble.utils.EnemyAction;

public class EnemyDisplay extends Table {

    private final AbstractEnemy enemy;

    private SpriteAnimation animation;
    private Table intent;

    public EnemyDisplay(AbstractEnemy enemy) {
        this.enemy = enemy;

        animation = this.enemy.animation;

        this.addActor(animation);

        Button btn = new Button(this.enemy.action.image);
        Label lbl = new Label("", new LabelStyle(FontHandler.BF_B24, Color.WHITE));

        lbl.setAlignment(Align.bottomRight);

        EnemyDisplay ed = this;

        intent = new Table() {
            public Button b = btn;
            public Label l = lbl;
            public void act(float delta) {
                EnemyAction action = enemy.action;
                b.setStyle(new ButtonStyle(action.image, action.image, action.image));
                if(action.type == EnemyAction.ActionType.ATTACK || action.type == EnemyAction.ActionType.ATTACK_BLOCK) l.setText(enemy.calculateDamage(action.amount));
                else if(action.type == EnemyAction.ActionType.BLOCK) l.setText(enemy.calculateDef(action.amount));
                this.setPosition(ed.getOriginX(), ed.getOriginY() + 15, Align.bottom);
            }
        };

        intent.align(Align.bottom);
        intent.add(btn).bottom().width(64).height(64);
        intent.row();
        intent.add(lbl).bottom().width(64).right().padTop(-lbl.getMinHeight());

        this.addActor(intent);



        this.enemy.animation.setAnimation("idle");
    }
}
