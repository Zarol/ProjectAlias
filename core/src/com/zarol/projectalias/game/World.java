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
			Vector2 playerSize = new Vector2(.4f, .4f);
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

		//Block
		Vector2 blockSize = new Vector2(.4f, .4f);
		TextureComponent textureComponent = new TextureComponent();
		textureComponent.loadTexture("Emerald", textureAtlas, "Block-Emerald");
		textureComponent.loadTexture("Gold", textureAtlas, "Block-Gold");
		textureComponent.loadTexture("Ruby", textureAtlas, "Block-Ruby");
		textureComponent.loadTexture("Sapphire", textureAtlas, "Block-Sapphire");

		//Create Emerald Block
		{
			Entity emerald = new Entity();
			emerald.attach(new PositionComponent(new Vector2(0,0)));
			emerald.attach(new CollisionComponent(new Rectangle(0,0, blockSize.x, blockSize.y)));
			emerald.attach(textureComponent);
			emerald.attach(new DrawableComponent(textureComponent.get("Emerald"), blockSize.x, blockSize.y));
			entityManager.add(emerald);
		}

		//Create Gold Blocks
		{
			Entity gold = new Entity();
			gold.attach(new PositionComponent(new Vector2(1,1)));
			gold.attach(new CollisionComponent(new Rectangle(0,0, blockSize.x, blockSize.y)));
			gold.attach(textureComponent);
			gold.attach(new DrawableComponent(textureComponent.get("Gold"), blockSize.x, blockSize.y));
			entityManager.add(gold);
		}

		//Create Ruby Blocks
		{
			Entity ruby = new Entity();
			ruby.attach(new PositionComponent(new Vector2(2,2)));
			ruby.attach(new CollisionComponent(new Rectangle(0,0, blockSize.x, blockSize.y)));
			ruby.attach(textureComponent);
			ruby.attach(new DrawableComponent(textureComponent.get("Ruby"), blockSize.x, blockSize.y));
			entityManager.add(ruby);
		}

		//Create Sapphire Blocks
		{
			Entity sapphire = new Entity();
			sapphire.attach(new PositionComponent(new Vector2(3,3)));
			sapphire.attach(new CollisionComponent(new Rectangle(0,0, blockSize.x, blockSize.y)));
			sapphire.attach(textureComponent);
			sapphire.attach(new DrawableComponent(textureComponent.get("Sapphire"), blockSize.x, blockSize.y));
			entityManager.add(sapphire);
		}
	}
}
