package com.fastcat.assemble;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.zumikua.webploader.desktop.DesktopNativeInterface;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.useVsync(true);
		config.setResizable(false);
		config.setBackBufferConfig(8, 8, 8, 8, 16, 0, 20);
		config.setTitle("Mousse's Adventure");
		new Lwjgl3Application(new WakTower(new DesktopNativeInterface()), config);
	}
}
