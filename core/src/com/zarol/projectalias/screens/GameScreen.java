package com.zarol.projectalias.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.zarol.projectalias.events.KeyDownEvent;
import com.zarol.projectalias.events.KeyUpEvent;
import com.zarol.projectalias.events.TouchDownEvent;
import com.zarol.projectalias.events.TouchUpEvent;
import com.zarol.projectalias.framework.Entity;
import com.zarol.projectalias.framework.EntityManager;
import com.zarol.projectalias.framework.EventManager;
import com.zarol.projectalias.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor {
	private EntityManager entityManager;
	private EventManager eventManager;
	private WorldRenderer renderer;

	private int width, height;

	@Override
	public void show() {
		entityManager = new EntityManager();
		eventManager = new EventManager();
		renderer = new WorldRenderer(entityManager, false);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		entityManager.update();
		for(Entity entity : entityManager.getAll()) {
			entity.update(delta);
		}
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
	}

	@Override
	public void resume() {
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
		eventManager.notify(new KeyDownEvent(keycode));
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		eventManager.notify(new KeyUpEvent(keycode));

		if (keycode == Keys.D) {
			renderer.setDebug(!renderer.isDebug());
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (Gdx.app.getType().equals(ApplicationType.Desktop)) {
			return false;
		}
		eventManager.notify(new TouchDownEvent(screenX, screenY, pointer, button, width, height));

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (Gdx.app.getType().equals(ApplicationType.Desktop)) {
			return false;
		}
		eventManager.notify(new TouchUpEvent(screenX, screenY, pointer, button, width, height));

		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
