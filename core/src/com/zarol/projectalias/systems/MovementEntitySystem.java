package com.zarol.projectalias.systems;

import com.badlogic.gdx.math.Vector2;
import com.zarol.projectalias.components.*;
import com.zarol.projectalias.framework.Entity;
import com.zarol.projectalias.framework.EntityManager;

/**
 * @author Zarol
 */
public class MovementEntitySystem extends EntitySystem {
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

		//todo: Check Collision Here

		if (acceleration.x == 0) {
			velocity.x *= dampen.x;
		}

		if (acceleration.y == 0) {
			velocity.y *= dampen.y;
		}

		//Clamps the velocity to maxVelocity
		velocity.x = Math.max(-maxVelocity.x, Math.min(maxVelocity.x, velocity.x));
		velocity.y = Math.max(-maxVelocity.y, Math.min(maxVelocity.y, velocity.y));

		if (velocity.isZero()) {
			if (!entity.has(IdleComponent.class)) {
				entity.attach(new IdleComponent());
			}
		} else {
			entity.detach(IdleComponent.class);
		}

		position.add(velocity.cpy().scl(delta));
	}
}
