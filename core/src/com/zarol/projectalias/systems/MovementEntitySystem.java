package com.zarol.projectalias.systems;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.zarol.projectalias.components.*;
import com.zarol.projectalias.framework.Entity;
import com.zarol.projectalias.framework.EntityManager;

/**
 * @author Zarol
 */
public class MovementEntitySystem extends EntitySystem {
	private Pool<Rectangle> rectanglePool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};

	public MovementEntitySystem(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void update(float delta) {
		for (Entity entity : entityManager.getAll()) {
			doAcceleration(entity);
			doPosition(entity, delta);
		}
	}

	private void doAcceleration(Entity entity) {
		if (!entity.has(AccelerationComponent.class)) {
			return;
		}

		Vector2 accelerate = entity.get(AccelerationComponent.class).getAccelerate();
		Vector2 acceleration = entity.get(AccelerationComponent.class).getAcceleration();

		if (entity.has(UpComponent.class)) {
			acceleration.y = accelerate.y;
		}

		if (entity.has(DownComponent.class)) {
			acceleration.y = -accelerate.y;
		}

		if (entity.has(LeftComponent.class)) {
			acceleration.x = -accelerate.x;
		}

		if (entity.has(RightComponent.class)) {
			acceleration.x = accelerate.x;
		}

		if (!entity.has(UpComponent.class) &&
				!entity.has(DownComponent.class) &&
				!entity.has(LeftComponent.class) &&
				!entity.has(RightComponent.class)) {
			acceleration.setZero();
		}
	}

	private void doPosition(Entity entity, float delta) {
		if (!entity.has(AccelerationComponent.class) || !entity.has(VelocityComponent.class) ||
				!entity.has(PositionComponent.class)) {
			return;
		}

		Vector2 acceleration = entity.get(AccelerationComponent.class).getAcceleration();
		Vector2 velocity = entity.get(VelocityComponent.class).getVelocity();
		Vector2 maxVelocity = entity.get(VelocityComponent.class).getMaxVelocity();
		Vector2 dampen = entity.get(VelocityComponent.class).getDampen();
		Vector2 position = entity.get(PositionComponent.class).getPosition();

		acceleration.scl(delta);
		velocity.add(acceleration);

		if (acceleration.x == 0) {
			velocity.x *= dampen.x;
		}

		if (acceleration.y == 0) {
			velocity.y *= dampen.y;
		}

		//Clamps the velocity to maxVelocity
		velocity.x = Math.max(-maxVelocity.x, Math.min(maxVelocity.x, velocity.x));
		velocity.y = Math.max(-maxVelocity.y, Math.min(maxVelocity.y, velocity.y));

		if (entity.has(PlayerControlledComponent.class)) {
			doCollision(entity, delta);
		}

		if (velocity.isZero()) {
			if (!entity.has(IdleComponent.class)) {
				entity.attach(new IdleComponent());
			}
		} else {
			entity.detach(IdleComponent.class);
		}

		position.add(velocity.cpy().scl(delta));
	}

	private void doCollision(Entity entity, float delta) {
		if (!entity.has(CollisionComponent.class) || !entity.has(VelocityComponent.class) ||
				!entity.has(PositionComponent.class)) {
			return;
		}

		Rectangle collision = entity.get(CollisionComponent.class).getBounds();
		Vector2 velocity = entity.get(VelocityComponent.class).getVelocity();
		Vector2 position = entity.get(PositionComponent.class).getPosition();

		Rectangle dxCollision = rectanglePool.obtain();
		dxCollision.set(collision);

		dxCollision.x += velocity.x * delta;
		for (Entity colliableEntity : entityManager.getAll()) {
			if (colliableEntity.equals(entity) || !colliableEntity.has(CollisionComponent.class)) {
				continue;
			}

			if (dxCollision.overlaps(colliableEntity.get(CollisionComponent.class).getBounds())) {
				velocity.x = 0;
				position.x -= velocity.x * delta;
				break;
			}
		}
		dxCollision.x = position.x;

		dxCollision.y += velocity.y * delta;
		for (Entity colliableEntity : entityManager.getAll()) {
			if (colliableEntity.equals(entity) || !colliableEntity.has(CollisionComponent.class)) {
				continue;
			}

			if (dxCollision.overlaps(colliableEntity.get(CollisionComponent.class).getBounds())) {
				velocity.y = 0;
				position.y -= velocity.y * delta;
				break;
			}
		}
		dxCollision.y = position.y;
	}
}
