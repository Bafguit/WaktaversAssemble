package com.fastcat.assemble;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fastcat.assemble.abstrcts.AbstractGame;
import com.fastcat.assemble.abstrcts.AbstractScreen;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.handlers.*;
import com.fastcat.assemble.screens.battle.BattleScreen;
import com.fastcat.assemble.utils.FillViewport;
import com.google.common.util.concurrent.FutureCallback;
import lombok.Getter;

public class MousseAdventure extends ApplicationAdapter {

	private static LifeCycle phase;

	public static MousseAdventure application;

	public static OrthographicCamera camera;
	public static PerspectiveCamera cam;
	public static Viewport viewport;

	public static BattleScreen battleScreen;

	public static AbstractGame game;
	public AbstractScreen screen;
	public Array<AbstractScreen> tempScreen = new Array<>();
	public static float tick;
	public static boolean fading;

	public static AbstractUI subText;

	@Getter
	private AssetManager assetManager;

	private Queue<Runnable> queuedTasks = new Queue<>();

	public ResourceHandler resourceHandler;

	public SpriteBatch sb;
	Texture img;
	
	@Override
	public void create () {
		phase = LifeCycle.STARTED;
		SettingHandler.initialize();
		InputHandler.getInstance();
		assetManager = new AssetManager();
		resourceHandler = new ResourceHandler(assetManager);

		application = this;
		sb = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera();
		float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
		camera.setToOrtho(false, w, h);
		camera.update();


		cam = new PerspectiveCamera(90, w, h);
		cam.position.set(w * 0.5f, h * 0.4f, 600 * InputHandler.scaleA);
		cam.lookAt(w * 0.5f, h * 0.5f,0);
		cam.near = 1f;
		cam.far = 1300 * InputHandler.scaleA;
		cam.update();

		viewport = new FillViewport(w, h);
		FileHandler.getInstance().loadFiles();
		FontHandler.getInstance();
		StringHandler.initialize();
		init();
	}

	private void init() {

		AsyncHandler.scheduleAsyncTask(
				() -> {
					FileHandler.getInstance().loadResources(resourceHandler);
					return new Object();
				},
				new FutureCallback<Object>() {
					@Override
					public void onSuccess(Object result) {
						phase = LifeCycle.LOADING;
						System.out.println("phase : " + phase);
					}

					@Override
					public void onFailure(Throwable t) {
						throw new RuntimeException(t);
					}
				});
	}

	private void load() {
		game = new AbstractGame();
		battleScreen = new BattleScreen();
		screen = battleScreen;
	}

	private void update() {
		subText = null;
		camera.update();
		float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
		cam.position.set(w * 0.5f, h * 0.4f, 600 * InputHandler.scaleA);
		cam.lookAt(w * 0.5f, h * 0.5f,0);
		cam.update();
		InputHandler.getInstance().update();

		if(game != null) {
			game.update();
		}

		if (tempScreen.size > 0) {
			AbstractScreen s = tempScreen.get(tempScreen.size - 1);
			if (s != null) {
				s.update();
			}
		} else if(screen != null) {
			screen.update();
		}
	}

	@Override
	public void render () {
		if (phase == LifeCycle.STARTED) {
			return;
		}

		if (phase == LifeCycle.LOADING) {
			if (resourceHandler.process()) phase = LifeCycle.FINISHING;
		}
		if (phase == LifeCycle.FINISHING) {
			load();
			phase = LifeCycle.ENDED;
		}

		ScreenUtils.clear(0, 0, 0, 1);

		tick = Gdx.graphics.getDeltaTime();

		if (phase == LifeCycle.ENDED) {
			/** Update */
			update();
		}
		sb.setProjectionMatrix(cam.combined);
		sb.enableBlending();
		sb.begin();

		/** Render Methods */
		// actionHandler.render(sb);
		super.render();

		if (phase == LifeCycle.ENDED) {
			if(screen != null) screen.render(tick);
			if (tempScreen.size > 0) {
				for (AbstractScreen s : tempScreen) {
					if (s != null) s.render(tick);
				}
			}
			if(game != null) game.render(sb);
			if(subText != null) subText.renderSub(sb);
        /*
        		sb.setColor(Color.WHITE);
        		sb.draw(cursor.img, InputHandler.mx, InputHandler.my - cursor.height / 2, cursor.width / 2, cursor.height / 2);
        */
		} else {
			float p = resourceHandler.getProgress();
			FontHandler.renderCenter(sb, FontHandler.NB30, "리소스 불러오는 중\n" + p * 100 + "%", 0,
					540 * InputHandler.scaleY, 1920 * InputHandler.scaleX);
		}
		sb.end();
	}
	
	@Override
	public void dispose () {
		sb.dispose();
		img.dispose();
	}

	public enum LifeCycle {
		STARTED,
		LOADING,
		FINISHING,
		ENDED
	}
}
