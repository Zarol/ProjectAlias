package com.zarol.projectalias.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.zarol.projectalias.components.*;
import com.zarol.projectalias.framework.Entity;
import com.zarol.projectalias.framework.EntityManager;
import com.zarol.projectalias.framework.EventManager;
import com.zarol.projectalias.systems.EntitySystem;
import com.zarol.projectalias.systems.MovementEntitySystem;
import com.zarol.projectalias.systems.PlayerControllerEntitySystem;
import com.zarol.projectalias.systems.SpriteEntitySystem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zarol
 */
public class World {
	private EntityManager entityManager;
	private EventManager eventManager;
	private List<EntitySystem> systemsList;

	public World(EntityManager entityManager, EventManager eventManager) {
		this.entityManager = entityManager;
		this.eventManager = eventManager;
		systemsList = new ArrayList<EntitySystem>();
		systemsList.add(new PlayerControllerEntitySystem(entityManager, eventManager));
		systemsList.add(new MovementEntitySystem(entityManager));
		systemsList.add(new SpriteEntitySystem(entityManager));
		initialize();
	}

	public void update(float delta) {
		for (Entity entity : entityManager.getAll()) {
			entity.update(delta);
		}
		entityManager.update();

		for (EntitySystem entitySystem : systemsList) {
			entitySystem.update(delta);
		}
	}

	public void cleanup() {
		for (Entity entity : entityManager.getAll()) {
			entity.cleanup();
		}

		for (EntitySystem entitySystem : systemsList) {
			entitySystem.cleanup();
		}
	}

	private void initialize() {
		for (EntitySystem entitySystem : systemsList) {
			entitySystem.initialize();
		}

		TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("images/textures/textures.atlas"));

		//Create player
		{
			Vector2 playerSize = new Vector2(.5f, .5f);
			Entity player = new Entity();
			player.attach(new AccelerationComponent(new Vector2(1000f, 1000f)));
			player.attach(new VelocityComponent(new Vector2(3f, 3f), new Vector2(0f, 0f), new Vector2(1f, 1f)));
			player.attach(new PositionComponent(new Vector2(7f, 2f)));
			player.attach(new CollisionComponent(new Rectangle(0f, 0f, playerSize.x, playerSize.y)));
			player.attach(new PlayerControlledComponent());
			AnimationComponent animationComponent = new AnimationComponent();
			animationComponent.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.UP.name(),
					textureAtlas, "Astronaut-Up", 0, 0, 0);
			animationComponent.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.DOWN.name(),
					textureAtlas, "Astronaut-Down", 0, 0, 0);
			animationComponent.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.LEFT.name(),
					textureAtlas, "Astronaut-Left", 0, 0, 0);
			animationComponent.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.RIGHT.name(),
					textureAtlas, "Astronaut-Right", 0, 0, 0);
			animationComponent.loadAnimation(
					SpriteEntitySystem.SpriteStatus.WALK.name() + SpriteEntitySystem.SpriteDirection.UP.name(),
					textureAtlas, "Astronaut-Up", 0, 0, 0);
			animationComponent.loadAnimation(
					SpriteEntitySystem.SpriteStatus.WALK.name() + SpriteEntitySystem.SpriteDirection.DOWN.name(),
					textureAtlas, "Astronaut-Down", 0, 0, 0);
			animationComponent.loadAnimation(
					SpriteEntitySystem.SpriteStatus.WALK.name() + SpriteEntitySystem.SpriteDirection.LEFT.name(),
					textureAtlas, "Astronaut-Left", 0, 8, .06f);
			animationComponent.loadAnimation(
					SpriteEntitySystem.SpriteStatus.WALK.name() + SpriteEntitySystem.SpriteDirection.RIGHT.name(),
					textureAtlas, "Astronaut-Right", 0, 8, .06f);
			player.attach(animationComponent);
			player.attach(new DrawableComponent(
					animationComponent.get(SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.DOWN.name()),
					playerSize.x, playerSize.y));
			entityManager.add(player);
		}

		//Block
		Vector2 blockSize = new Vector2(.5f, .5f);

		//Create Emerald Block
		{
			AnimationComponent emeraldAnimation = new AnimationComponent();
			emeraldAnimation.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.UP.name(),
					textureAtlas, "Block-Emerald", 0, 0, 0);
			emeraldAnimation.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.DOWN.name(),
					textureAtlas, "Block-Emerald", 0, 0, 0);
			emeraldAnimation.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.LEFT.name(),
					textureAtlas, "Block-Emerald", 0, 0, 0);
			emeraldAnimation.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.RIGHT.name(),
					textureAtlas, "Block-Emerald", 0, 0, 0);

			Entity emerald = new Entity();
			emerald.attach(new PositionComponent(new Vector2(0f, 0f)));
			emerald.attach(new CollisionComponent(new Rectangle(0f, 0f, blockSize.x, blockSize.y)));
			emerald.attach(new IdleComponent());
			emerald.attach(emeraldAnimation);
			emerald.attach(new DrawableComponent(emeraldAnimation.get(SpriteEntitySystem.SpriteStatus.IDLE.name()
					+ SpriteEntitySystem.SpriteDirection.DOWN.name()), blockSize.x, blockSize.y));
			entityManager.add(emerald);
		}


		//Create Gold Blocks
		{
			AnimationComponent goldAnimation = new AnimationComponent();
			goldAnimation.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.UP.name(),
					textureAtlas, "Block-Gold", 0, 0, 0);
			goldAnimation.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.DOWN.name(),
					textureAtlas, "Block-Gold", 0, 0, 0);
			goldAnimation.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.LEFT.name(),
					textureAtlas, "Block-Gold", 0, 0, 0);
			goldAnimation.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.RIGHT.name(),
					textureAtlas, "Block-Gold", 0, 0, 0);

			Entity gold = new Entity();
			gold.attach(new PositionComponent(new Vector2(1f, 1f)));
			gold.attach(new CollisionComponent(new Rectangle(0f, 0f, blockSize.x, blockSize.y)));
			gold.attach(new IdleComponent());
			gold.attach(goldAnimation);
			gold.attach(new DrawableComponent(goldAnimation.get(SpriteEntitySystem.SpriteStatus.IDLE.name()
					+ SpriteEntitySystem.SpriteDirection.DOWN.name()), blockSize.x, blockSize.y));
			entityManager.add(gold);
		}

		//Create Ruby Blocks
		{
			AnimationComponent rubyAnimation = new AnimationComponent();
			rubyAnimation.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.UP.name(),
					textureAtlas, "Block-Ruby", 0, 0, 0);
			rubyAnimation.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.DOWN.name(),
					textureAtlas, "Block-Ruby", 0, 0, 0);
			rubyAnimation.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.LEFT.name(),
					textureAtlas, "Block-Ruby", 0, 0, 0);
			rubyAnimation.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.RIGHT.name(),
					textureAtlas, "Block-Ruby", 0, 0, 0);

			Entity ruby = new Entity();
			ruby.attach(new PositionComponent(new Vector2(2f, 2f)));
			ruby.attach(new CollisionComponent(new Rectangle(0f, 0f, blockSize.x, blockSize.y)));
			ruby.attach(new IdleComponent());
			ruby.attach(rubyAnimation);
			ruby.attach(new DrawableComponent(rubyAnimation.get(SpriteEntitySystem.SpriteStatus.IDLE.name()
					+ SpriteEntitySystem.SpriteDirection.DOWN.name()), blockSize.x, blockSize.y));
			entityManager.add(ruby);
		}

		//Create Sapphire Blocks
		{
			AnimationComponent sapphireAnimation = new AnimationComponent();
			sapphireAnimation.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.UP.name(),
					textureAtlas, "Block-Sapphire", 0, 0, 0);
			sapphireAnimation.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.DOWN.name(),
					textureAtlas, "Block-Sapphire", 0, 0, 0);
			sapphireAnimation.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.LEFT.name(),
					textureAtlas, "Block-Sapphire", 0, 0, 0);
			sapphireAnimation.loadAnimation(
					SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.RIGHT.name(),
					textureAtlas, "Block-Sapphire", 0, 0, 0);

			Entity sapphire = new Entity();
			sapphire.attach(new PositionComponent(new Vector2(3f, 3f)));
			sapphire.attach(new CollisionComponent(new Rectangle(0f, 0f, blockSize.x, blockSize.y)));
			sapphire.attach(new IdleComponent());
			sapphire.attach(sapphireAnimation);
			sapphire.attach(new DrawableComponent(sapphireAnimation.get(SpriteEntitySystem.SpriteStatus.IDLE.name()
					+ SpriteEntitySystem.SpriteDirection.DOWN.name()), blockSize.x, blockSize.y));
			entityManager.add(sapphire);
		}
	}
}
