package com.fastcat.assemble;

import java.util.LinkedList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.FxaaEffect;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.fastcat.assemble.abstracts.AbstractGame;
import com.fastcat.assemble.abstracts.AbstractScreen;
import com.fastcat.assemble.abstracts.AbstractStage;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.abstracts.AbstractUI.UIData;
import com.fastcat.assemble.handlers.*;
import com.fastcat.assemble.stages.LoadingStage;
import com.fastcat.assemble.stages.battle.BattleStage;
import com.fastcat.assemble.stages.mainmenu.MainMenuScreen;
import com.fastcat.assemble.utils.FillViewport;
import io.github.zumikua.webploader.common.WebPLoaderFactory;
import io.github.zumikua.webploader.common.WebPLoaderNativeInterface;
import io.github.zumikua.webploader.common.WebPPixmapFactory;

public class WakTower extends ApplicationAdapter {

	private static String log = "";

	public static WakTower application;

	public static OrthographicCamera camera;
	public static Viewport viewport;

	public static PolygonSpriteBatch psb;
	public static SkeletonRenderer sr;
	public static MainMenuScreen mainMenuScreen;

	public static AbstractGame game;
	public static float tick;
	public static boolean fading;

	public static Stage stage;

	public static LinkedList<Stage> stages = new LinkedList<>();

	public static WebPPixmapFactory pixmapFactory;
	public static WebPLoaderFactory webpFactory;

	public static AbstractUI subText;
	public AbstractScreen screen;
	public Array<AbstractScreen> tempScreen = new Array<>();

	public BattleStage battleStage;

	public SpriteBatch sb;
	public VfxManager vfxManager;

	private FxaaEffect fxaa;

	public static boolean debug = true;

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
		camera.setToOrtho(false, 1920, 1080);
		camera.update();
		vfxManager = new VfxManager(Pixmap.Format.RGBA8888);
		fxaa = new FxaaEffect();
		vfxManager.addEffect(fxaa);

		viewport = new FillViewport(1920, 1080);
		viewport.setCamera(camera);
		FileHandler.getInstance();
		FontHandler.getInstance();
		DataHandler.getInstance();
		uiData = DataHandler.getInstance().uiData.get("loading");
		
		stage = new LoadingStage();
		TooltipManager m = TooltipManager.getInstance();
		m.animations = false;
		m.initialTime = 0.2f;
		m.resetTime = 0.1f;
		
	}

	public static String getLog() {
		return log;
	}

	public static String log(String text) {
		log += "\n" + text;
		return log;
	}

	private void load() {
		DataHandler.getInstance().loadAsync();
		//game = new AbstractGame();
		GroupHandler.initialize();
	}

	private void act(float delta) {
		InputHandler.getInstance().update();

		if(game != null) {
			game.update();
		}

		if(stages.size() > 0) {
			Gdx.input.setInputProcessor(stages.getLast());
		} else if(stage != null) {
			Gdx.input.setInputProcessor(stage);
		}

		if(stage != null) {
			stage.act(delta);
		}

		if(stages.size() > 0) {
			for(Stage s : stages) {
				s.act(delta);
			}
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

		tick = Gdx.graphics.getDeltaTime();

		act(tick);

		ScreenUtils.clear(0, 0, 0, 1);

        /*vfxManager.cleanUpBuffers();
        vfxManager.beginInputCapture();*/

		sb.setProjectionMatrix(camera.combined);
		sb.enableBlending();
		sb.begin();

		/** Render Methods */
		// actionHandler.render(sb);
		super.render();

		if(stage != null) stage.draw();

		if(stages.size() > 0) {
			for(Stage s : stages) {
				s.draw();
			}
		}

		if(game != null) game.render(sb);

		sb.end();
		
        /*vfxManager.endInputCapture();
        vfxManager.applyEffects();
        vfxManager.renderToScreen();*/
	}
	
	@Override
	public void dispose () {
		sb.dispose();
		vfxManager.dispose();
		fxaa.dispose();
	}
}
