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
			Vector2 playerSize = new Vector2(1f, 1f);
			player = new Entity();
			player.attach(new AccelerationComponent());
			player.attach(new VelocityComponent(new Vector2(4f, 4f), new Vector2(0,0), new Vector2(1,1)));
			player.attach(new PositionComponent(new Vector2(7, 2)));
			player.attach(new CollisionComponent(new Rectangle(0, 0, playerSize.x, playerSize.y)));
			player.attach(new PlayerControllerComponent(eventManager));
			TextureComponent textureComponent = new TextureComponent();
			textureComponent.loadTexture("IdleLeft", textureAtlas, "Astronaut-Left", 0);
			textureComponent.loadTexture("IdleRight", textureAtlas, "Astronaut-Right", 0);
			textureComponent.loadTexture("IdleUp", textureAtlas, "Astronaut-Up", 0);
			textureComponent.loadTexture("IdleDown", textureAtlas, "Astronaut-Down", 0);
			player.attach(textureComponent);
			AnimationComponent animationComponent = new AnimationComponent();
			animationComponent.loadAnimation("WalkingLeft", textureAtlas, "Astronaut-Left", 0, 8, .06f);
			animationComponent.loadAnimation("WalkingRight", textureAtlas, "Astronaut-Right", 0, 8, .06f);
			animationComponent.loadAnimation("WalkingUp", textureAtlas, "Astronaut-Up", 0, 0, .06f);
			animationComponent.loadAnimation("WalkingDown", textureAtlas, "Astronaut-Down", 0, 0, .06f);
			player.attach(animationComponent);
			player.attach(new DrawableComponent(textureComponent.get("IdleLeft"), playerSize.x, playerSize.y));
			entityManager.add(player);
		}
	}
}
