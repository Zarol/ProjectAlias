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

	private static final Vector2 BLOCK_SIZE = new Vector2(.45f, .45f);
	private static final Vector2 PLAYER_SIZE = new Vector2(.5f, .5f);

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

		AnimationComponent emeraldAnimation = loadBlockAnimation(textureAtlas, "Block-Emerald");
		AnimationComponent goldAnimation = loadBlockAnimation(textureAtlas, "Block-Gold");
		AnimationComponent rubyAnimation = loadBlockAnimation(textureAtlas, "Block-Ruby");
		AnimationComponent sapphireAnimation = loadBlockAnimation(textureAtlas, "Block-Sapphire");
		AnimationComponent pelletAnimation = loadBlockAnimation(textureAtlas, "Pellet");
		for (int x = 0; x < Level1.level.length; x++) {
			for (int y = 0; y < Level1.level[0].length; y++) {
				Entity entity = null;
				switch (Level1.level[x][y]) {
					case 'e':
						entity = createBlockEntity(new Vector2(x * .5f, y * .5f), emeraldAnimation);
						break;
					case 'g':
						entity = createBlockEntity(new Vector2(x * .5f, y * .5f), goldAnimation);
						break;
					case 'r':
						entity = createBlockEntity(new Vector2(x * .5f, y * .5f), rubyAnimation);
						break;
					case 's':
						entity = createBlockEntity(new Vector2(x * .5f, y * .5f), sapphireAnimation);
						break;
					case 'p':
						entity = createPlayerEntity(textureAtlas, new Vector2(x * .5f, y * .5f));
						break;
					case ' ':
						entity = createPelletEntity(new Vector2(x * .5f + .15f, y * .5f + .15f), pelletAnimation);
				}
				if (entity != null) {
					entityManager.add(entity);
				}
			}
		}
	}

	private Entity createPlayerEntity(TextureAtlas textureAtlas, Vector2 position) {
		Entity player = new Entity();
		player.attach(new AccelerationComponent(new Vector2(1000f, 1000f)));
		player.attach(new VelocityComponent(new Vector2(3f, 3f), new Vector2(0f, 0f), new Vector2(1f, 1f)));
		player.attach(new PositionComponent(new Vector2(position)));
		player.attach(new CollisionComponent(new Rectangle(0f, 0f, PLAYER_SIZE.x, PLAYER_SIZE.y)));
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
				PLAYER_SIZE.x, PLAYER_SIZE.y));

		return player;
	}

	private AnimationComponent loadBlockAnimation(TextureAtlas textureAtlas, String regionName) {
		AnimationComponent animation = new AnimationComponent();
		animation.loadAnimation(
				SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.UP.name(),
				textureAtlas, regionName, 0, 0, 0);
		animation.loadAnimation(
				SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.DOWN.name(),
				textureAtlas, regionName, 0, 0, 0);
		animation.loadAnimation(
				SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.LEFT.name(),
				textureAtlas, regionName, 0, 0, 0);
		animation.loadAnimation(
				SpriteEntitySystem.SpriteStatus.IDLE.name() + SpriteEntitySystem.SpriteDirection.RIGHT.name(),
				textureAtlas, regionName, 0, 0, 0);

		return animation;
	}

	private Entity createBlockEntity(Vector2 position, AnimationComponent animationComponent) {
		Entity block = new Entity();
		block.attach(new PositionComponent(position));
		block.attach(new CollisionComponent(new Rectangle(0f, 0f, BLOCK_SIZE.x, BLOCK_SIZE.y)));
		block.attach(new IdleComponent());
		block.attach(animationComponent);
		block.attach(new DrawableComponent(animationComponent.get(SpriteEntitySystem.SpriteStatus.IDLE.name()
				+ SpriteEntitySystem.SpriteDirection.DOWN.name()), BLOCK_SIZE.x, BLOCK_SIZE.y));

		return block;
	}

	private Entity createPelletEntity(Vector2 position, AnimationComponent animationComponent) {
		Entity pellet = new Entity();
		pellet.attach(new PositionComponent(new Vector2(position.x, position.y)));
		pellet.attach(new CollisionComponent(new Rectangle(0f, 0f, BLOCK_SIZE.x / 3, BLOCK_SIZE.y / 3)));
		pellet.attach(new IdleComponent());
		pellet.attach(new ScorableComponent());
		pellet.attach(animationComponent);
		pellet.attach(new DrawableComponent(animationComponent.get(SpriteEntitySystem.SpriteStatus.IDLE.name()
				+ SpriteEntitySystem.SpriteDirection.DOWN.name()), BLOCK_SIZE.x / 3, BLOCK_SIZE.y / 3));

		return pellet;
	}
}
