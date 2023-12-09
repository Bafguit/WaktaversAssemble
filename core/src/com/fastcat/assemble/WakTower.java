package com.fastcat.assemble;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.fastcat.assemble.abstracts.AbstractGame;
import com.fastcat.assemble.abstracts.AbstractScreen;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.abstracts.AbstractUI.UIData;
import com.fastcat.assemble.handlers.*;
import com.fastcat.assemble.screens.battle.BattleScreen;
import com.fastcat.assemble.screens.mainmenu.MainMenuScreen;
import com.fastcat.assemble.utils.FillViewport;
import io.github.zumikua.webploader.common.WebPLoaderFactory;
import io.github.zumikua.webploader.common.WebPLoaderNativeInterface;
import io.github.zumikua.webploader.common.WebPPixmapFactory;
import lombok.Getter;

public class WakTower extends ApplicationAdapter {

	public static WakTower application;

	public static OrthographicCamera camera;
	public static Viewport viewport;

	public static PolygonSpriteBatch psb;
	public static SkeletonRenderer sr;
	public static MainMenuScreen mainMenuScreen;
	public static BattleScreen battleScreen;

	public static AbstractGame game;
	public static float tick;
	public static boolean fading;

	public static WebPPixmapFactory pixmapFactory;
	public static WebPLoaderFactory webpFactory;

	public static AbstractUI subText;
	public AbstractScreen screen;
	public Array<AbstractScreen> tempScreen = new Array<>();

	public SpriteBatch sb;

	private boolean isLoaded = false;

	private UIData uiData;

	public WakTower(WebPLoaderNativeInterface nativeInterface) {
		webpFactory = new WebPLoaderFactory(nativeInterface);
		pixmapFactory = webpFactory.getPixmapFactory();
	}
	
	@Override
	public void create () {
		SettingHandler.initialize();
		InputHandler.getInstance();

		application = this;
		psb = new PolygonSpriteBatch();
		sr = new SkeletonRenderer();
		sb = new SpriteBatch();
		camera = new OrthographicCamera();
		float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
		camera.setToOrtho(false, w, h);
		camera.update();

		viewport = new FillViewport(w, h);
		FileHandler.getInstance();
		FontHandler.getInstance();
		DataHandler.getInstance();
		uiData = DataHandler.getInstance().uiData.get("loading");
	}

	private void load() {
		DataHandler.getInstance().loadAsync();
		//game = new AbstractGame();
		mainMenuScreen = new MainMenuScreen();
		screen = mainMenuScreen;
	}

	private void update() {
		subText = null;
		camera.update();
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
		if(!FileHandler.isFinished()) {
			FileHandler.loadAsset();
			if(FileHandler.isFinished()) {
				isLoaded = true;
				load();
			}
		}

		ScreenUtils.clear(0, 0, 0, 1);

		tick = Gdx.graphics.getDeltaTime();

		if (isLoaded) {
			/** Update */
			update();
		}
		sb.setProjectionMatrix(camera.combined);
		sb.enableBlending();
		sb.begin();

		/** Render Methods */
		// actionHandler.render(sb);
		super.render();

		if (isLoaded) {
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
			float p = FileHandler.getProcess();
			FontHandler.renderCenter(sb, FontHandler.NB30, uiData.text[0] + "\n" + String.format("%.1f", p * 100) + "%", 0,
					540 * InputHandler.scaleY, 1920 * InputHandler.scaleX);
		}
		sb.end();
	}

	public static void setScreen(AbstractScreen sc) {
		sc.update();
		application.screen = sc;
	}
	
	@Override
	public void dispose () {
		sb.dispose();
	}
}
