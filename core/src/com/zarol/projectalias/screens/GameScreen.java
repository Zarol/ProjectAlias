package com.zarol.projectalias.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.zarol.projectalias.controller.BobController;
import com.zarol.projectalias.model.World;
import com.zarol.projectalias.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor {
	private World world;
	private WorldRenderer renderer;
	private BobController controller;

	private int width, height;

	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world, false);
		controller = new BobController(world);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		controller.update(delta);
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		renderer.setSize(this.width, this.height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT) {
			controller.leftPressed();
		}
		if (keycode == Keys.RIGHT) {
			controller.rightPressed();
		}
		if (keycode == Keys.Z) {
			controller.jumpPressed();
		}
		if (keycode == Keys.X) {
			controller.firePressed();
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT) {
			controller.leftReleased();
		}
		if (keycode == Keys.RIGHT) {
			controller.rightReleased();
		}
		if (keycode == Keys.Z) {
			controller.jumpReleased();
		}
		if (keycode == Keys.X) {
			controller.fireReleased();
		}
		if (keycode == Keys.D) {
			renderer.setDebug(!renderer.isDebug());
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (Gdx.app.getType().equals(ApplicationType.Desktop)) {
			return false;
		}

		if (screenX < width / 2 && screenY > height / 2) {
			controller.leftPressed();
		}
		if (screenX > width / 2 && screenY > height / 2) {
			controller.rightPressed();
		}
		if (screenY < height / 2) {
			controller.jumpPressed();
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (Gdx.app.getType().equals(ApplicationType.Desktop)) {
			return false;
		}

		if (screenX < width / 2 && screenY > height / 2) {
			controller.leftReleased();
		}
		if (screenX > width / 2 && screenY > height / 2) {
			controller.rightReleased();
		}
		if (screenY < height / 2) {
			controller.jumpReleased();
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
