package com.zarol.projectalias.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.zarol.projectalias.events.*;
import com.zarol.projectalias.framework.EntityManager;
import com.zarol.projectalias.framework.EventManager;
import com.zarol.projectalias.game.World;
import com.zarol.projectalias.view.WorldRenderer;

public class GameScreen implements Screen, GestureDetector.GestureListener {
	private EntityManager entityManager;
	private EventManager eventManager;
	private World world;
	private WorldRenderer worldRenderer;

	private int width, height;

	@Override
	public void show() {
		entityManager = new EntityManager();
		eventManager = new EventManager();
		world = new World(entityManager, eventManager);
		worldRenderer = new WorldRenderer(entityManager, false);
		Gdx.input.setInputProcessor(new GestureDetector(this));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		entityManager.update();
		world.update(delta);
		worldRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		worldRenderer.setSize(this.width, this.height);
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
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		if(Math.abs(velocityX)>Math.abs(velocityY)){
			if(velocityX>0){
				eventManager.notify(new SwipeEvent(SwipeListener.SwipeDirection.RIGHT));
			}else{
				eventManager.notify(new SwipeEvent(SwipeListener.SwipeDirection.LEFT));
			}
		}else{
			if(velocityY>0){
				eventManager.notify(new SwipeEvent(SwipeListener.SwipeDirection.DOWN));
			}else{
				eventManager.notify(new SwipeEvent(SwipeListener.SwipeDirection.UP));
			}
		}
		return true;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

	@Override
	public void pinchStop() {

	}
}
