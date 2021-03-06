package com.zarol.projectalias.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zarol.projectalias.ProjectAlias;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.width = 540;
		config.height = 960;
		new LwjglApplication(new ProjectAlias(), config);
	}
}
