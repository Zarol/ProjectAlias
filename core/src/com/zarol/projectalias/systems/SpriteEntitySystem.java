package com.zarol.projectalias.systems;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.zarol.projectalias.components.*;
import com.zarol.projectalias.framework.Entity;
import com.zarol.projectalias.framework.EntityManager;

/**
 * @author Zarol
 */
public class SpriteEntitySystem extends EntitySystem {
	public enum SpriteStatus {
		IDLE, WALK
	}

	public enum SpriteDirection {
		UP, DOWN, LEFT, RIGHT
	}

	public SpriteEntitySystem(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void update(float delta) {
		for (Entity entity : entityManager.getAll()) {
			doTexture(entity);
		}
	}

	private void doTexture(Entity entity) {
		if (!entity.has(DrawableComponent.class) || !entity.has(AnimationComponent.class)) {
			return;
		}
		DrawableComponent drawable = entity.get(DrawableComponent.class);
		AnimationComponent animation = entity.get(AnimationComponent.class);

		SpriteStatus status;
		SpriteDirection direciton;

		if (!entity.has(IdleComponent.class)) {
			status = SpriteStatus.WALK;
		} else {
			status = SpriteStatus.IDLE;
		}

		if (entity.has(UpComponent.class)) {
			direciton = SpriteDirection.UP;
		} else if (entity.has(DownComponent.class)) {
			direciton = SpriteDirection.DOWN;
		} else if (entity.has(LeftComponent.class)) {
			direciton = SpriteDirection.LEFT;
		} else if (entity.has(RightComponent.class)) {
			direciton = SpriteDirection.RIGHT;
		} else {
			direciton = SpriteDirection.DOWN;
		}

		String texture = status.name() + direciton.name();
		if (animation.has(texture)) {
			drawable.setTextureRegion(animation.get(texture));
		} else {
			drawable.setTextureRegion(new TextureRegion());
		}
	}
}
