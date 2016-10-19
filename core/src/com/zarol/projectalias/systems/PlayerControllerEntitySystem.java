package com.zarol.projectalias.systems;

import com.zarol.projectalias.components.*;
import com.zarol.projectalias.events.SwipeEvent;
import com.zarol.projectalias.events.SwipeListener;
import com.zarol.projectalias.framework.Entity;
import com.zarol.projectalias.framework.EntityManager;
import com.zarol.projectalias.framework.EventManager;

/**
 * @author Zarol
 */
public class PlayerControllerEntitySystem extends EntitySystem {
	private Entity character;

	public PlayerControllerEntitySystem(EntityManager entityManager, EventManager eventManager) {
		this.entityManager = entityManager;
		this.eventManager = eventManager;
	}

	@Override
	public void initialize() {
		eventManager.listen(SwipeEvent.class, new SwipeListener() {
			@Override
			public void swipe(SwipeDirection direction) {
				switch (direction) {
					case UP:
						doUp();
						break;
					case DOWN:
						doDown();
						break;
					case LEFT:
						doLeft();
						break;
					case RIGHT:
						doRight();
						break;
				}
			}
		});
	}

	@Override
	public void update(float delta) {
		//Perhaps another way to do this would be to send an event whenever this component got moved? Would save O(n) operations.
		//Why is this component moving anyway? I'm not sure, but I suppose it could.
		//I suppose multiple entities could also have this component, should I allow this or how would I fix this?
		for (Entity entity : entityManager.getAll()) {
			if (entity.has(PlayerControlledComponent.class)) {
				character = entity;
			}
		}
	}

	private void doUp() {
		if (character.has(UpComponent.class)) {
			return;
		}

		if (!character.has(AccelerationComponent.class) || !character.has(VelocityComponent.class)) {
			return;
		}

		character.get(AccelerationComponent.class).getAcceleration().setZero();
		character.get(VelocityComponent.class).getVelocity().setZero();

		character.attach(new UpComponent());
		character.detach(DownComponent.class);
		character.detach(LeftComponent.class);
		character.detach(RightComponent.class);

		character.detach(IdleComponent.class);
	}

	private void doDown() {
		if (character.has(DownComponent.class)) {
			return;
		}

		if (!character.has(AccelerationComponent.class) || !character.has(VelocityComponent.class)) {
			return;
		}

		character.get(AccelerationComponent.class).getAcceleration().setZero();
		character.get(VelocityComponent.class).getVelocity().setZero();

		character.detach(UpComponent.class);
		character.attach(new DownComponent());
		character.detach(LeftComponent.class);
		character.detach(RightComponent.class);

		character.detach(IdleComponent.class);
	}

	private void doLeft() {
		if (character.has(LeftComponent.class)) {
			return;
		}

		if (!character.has(AccelerationComponent.class) || !character.has(VelocityComponent.class)) {
			return;
		}

		character.get(AccelerationComponent.class).getAcceleration().setZero();
		character.get(VelocityComponent.class).getVelocity().setZero();

		character.detach(UpComponent.class);
		character.detach(DownComponent.class);
		character.attach(new LeftComponent());
		character.detach(RightComponent.class);

		character.detach(IdleComponent.class);
	}

	private void doRight() {
		if (character.has(RightComponent.class)) {
			return;
		}

		if (!character.has(AccelerationComponent.class) || !character.has(VelocityComponent.class)) {
			return;
		}

		character.get(AccelerationComponent.class).getAcceleration().setZero();
		character.get(VelocityComponent.class).getVelocity().setZero();

		character.detach(UpComponent.class);
		character.detach(DownComponent.class);
		character.detach(LeftComponent.class);
		character.attach(new RightComponent());

		character.detach(IdleComponent.class);
	}
}
