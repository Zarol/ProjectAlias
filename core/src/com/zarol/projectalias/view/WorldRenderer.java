package com.zarol.projectalias.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.zarol.projectalias.components.CollisionComponent;
import com.zarol.projectalias.components.DrawableComponent;
import com.zarol.projectalias.components.PositionComponent;
import com.zarol.projectalias.framework.Entity;
import com.zarol.projectalias.framework.EntityManager;

public class WorldRenderer {
	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;

	private EntityManager entityManager;
	private OrthographicCamera cam;

	private ShapeRenderer debugRenderer = new ShapeRenderer();

	private SpriteBatch spriteBatch;
	private boolean debug = false;
	private int width;
	private int height;

	public WorldRenderer(EntityManager entityManager, boolean debug) {
		this.entityManager = entityManager;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		this.debug = debug;
		spriteBatch = new SpriteBatch();
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void render() {
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		for (Entity entity : entityManager.getAll()) {
			if (entity.has(DrawableComponent.class) && entity.has(PositionComponent.class)) {
				DrawableComponent drawableComponent = entity.get(DrawableComponent.class);
				Vector2 position = entity.get(PositionComponent.class).getPosition();
				spriteBatch.draw(drawableComponent.getTextureRegion(), position.x, position.y,
						drawableComponent.getWidth(), drawableComponent.getHeight());
			}
		}
		spriteBatch.end();

		if (debug) {
			drawDebug();
		}
	}

	private void drawDebug() {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Line);
		for (Entity entity : entityManager.getAll()) {
			if (entity.has(CollisionComponent.class) && entity.has(PositionComponent.class)) {
				Rectangle rectangle = entity.get(CollisionComponent.class).getBounds();
				debugRenderer.setColor(Color.RED);
				debugRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
			}
		}
		debugRenderer.end();
	}
}
