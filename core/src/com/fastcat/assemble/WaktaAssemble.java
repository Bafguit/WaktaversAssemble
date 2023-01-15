package com.fastcat.assemble;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class WaktaAssemble extends ApplicationAdapter {

	public static WaktaAssemble game;

	public static float tick;
	public static boolean fading;

	public SpriteBatch sb;
	Texture img;
	
	@Override
	public void create () {
		game = this;
		sb = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		tick = Gdx.graphics.getDeltaTime();

		sb.begin();
		sb.draw(img, 0, 0);
		sb.end();
	}
	
	@Override
	public void dispose () {
		sb.dispose();
		img.dispose();
	}
}
