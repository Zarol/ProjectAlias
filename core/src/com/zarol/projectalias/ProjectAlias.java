package com.zarol.projectalias;

import com.badlogic.gdx.Game;
import com.zarol.projectalias.screens.GameScreen;

public class ProjectAlias extends Game {
	@Override
	public void create() {
		setScreen(new GameScreen());
	}
}
