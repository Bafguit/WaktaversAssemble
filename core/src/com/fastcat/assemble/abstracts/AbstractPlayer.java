package com.fastcat.assemble.abstracts;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.utils.SpineAnimation;

public class AbstractPlayer extends AbstractEntity{

    protected TextureAtlas atlas_back;
    protected FileHandle skeleton_back;
    public SpineAnimation animation_front;
    public SpineAnimation animation_back;

    public boolean lookingBack = false;

    public AbstractPlayer(String id, int attack, int health, int def, int res) {
        super(id, attack, health, def, res, EntityRarity.OPERATOR);
        isPlayer = true;

    }

    @Override
    protected void setAnimation() {
        atlas = FileHandler.atlas.get(id + "_front");
        skeleton = FileHandler.skeleton.get(id + "_front");
        animation_front = new SpineAnimation(atlas, skeleton);
        animation_front.resetAnimation();

        atlas_back = FileHandler.atlas.get(id + "_back");
        skeleton_back = FileHandler.skeleton.get(id + "_back");
        animation_back = new SpineAnimation(atlas_back, skeleton_back);
        animation_back.resetAnimation();

        animation = animation_front;
    }

    @Override
    public void updateDir(AbstractSkill.SkillDir dir) {
        super.updateDir(dir);
        if(dir == AbstractSkill.SkillDir.UP) {
            animation = animation_back;
        } else {
            animation = animation_front;
        }
    }
}
