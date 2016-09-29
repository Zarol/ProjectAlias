package com.zarol.projectalias.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.zarol.projectalias.components.*;
import com.zarol.projectalias.framework.Entity;
import com.zarol.projectalias.framework.EntityManager;
import com.zarol.projectalias.framework.EventManager;

/**
 * @author Zarol
 */
public class World {
	private EntityManager entityManager;
	private EventManager eventManager;
	Entity player;

	public World(EntityManager entityManager, EventManager eventManager) {
		this.entityManager = entityManager;
		this.eventManager = eventManager;
		initialize();
	}

	public void update(float delta) {
		for (Entity entity : entityManager.getAll()) {
			entity.update(delta);
		}
		entityManager.update();
	}

	private void initialize() {
		TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("images/textures/textures.atlas"));

		//Create player
		{
			Vector2 playerSize = new Vector2(.5f, .5f);
			player = new Entity();
			player.attach(new AccelerationComponent());
			player.attach(new VelocityComponent(new Vector2(4f, Float.MAX_VALUE)));
			player.attach(new PositionComponent(new Vector2(7, 2)));
			player.attach(new CollisionComponent(new Rectangle(0, 0, playerSize.x, playerSize.y)));
			player.attach(new PlayerControllerComponent(eventManager));
			TextureComponent textureComponent = new TextureComponent();
			textureComponent.loadTexture("IdleLeft", textureAtlas, "bob", 1);
			textureComponent.loadTexture("IdleRight", textureAtlas, "bob", 1, true, false);
			textureComponent.loadTexture("JumpLeft", textureAtlas, "bob-up");
			textureComponent.loadTexture("JumpRight", textureAtlas, "bob-up", true, false);
			textureComponent.loadTexture("FallLeft", textureAtlas, "bob-down");
			textureComponent.loadTexture("FallRight", textureAtlas, "bob-down", true, false);
			player.attach(textureComponent);
			AnimationComponent animationComponent = new AnimationComponent();
			animationComponent.loadAnimation("WalkingLeft", textureAtlas, "bob", 2, 6, .06f);
			animationComponent.loadAnimation("WalkingRight", textureAtlas, "bob", 2, 6, .06f, true, false);
			player.attach(animationComponent);
			player.attach(new DrawableComponent(textureComponent.get("IdleLeft"), playerSize.x, playerSize.y));
			entityManager.add(player);
		}
	}
}
