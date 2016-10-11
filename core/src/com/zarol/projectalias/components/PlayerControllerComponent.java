package com.zarol.projectalias.components;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.zarol.projectalias.events.*;
import com.zarol.projectalias.framework.Component;
import com.zarol.projectalias.framework.EventManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zarol
 */
public class PlayerControllerComponent extends Component {
	private enum Keys {
		LEFT, RIGHT, UP, DOWN
	}

	private static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();

	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
	}

	private static final float ACCELERATION = 1000f;

	public PlayerControllerComponent(EventManager eventManager) {
		eventManager.listen(KeyDownEvent.class, new KeyDownListener() {
			@Override
			public void keyDown(int keycode) {
				if (keycode == Input.Keys.LEFT) {
					keys.get(keys.put(Keys.LEFT, true));
				}
				if (keycode == Input.Keys.RIGHT) {
					keys.get(keys.put(Keys.RIGHT, true));
				}
				if (keycode == Input.Keys.UP) {
					keys.get(keys.put(Keys.UP, true));
				}
				if (keycode == Input.Keys.DOWN) {
					keys.get(keys.put(Keys.DOWN, true));
				}
			}
		});
		eventManager.listen(KeyUpEvent.class, new KeyUpListener() {
			@Override
			public void keyUp(int keycode) {
				if (keycode == Input.Keys.LEFT) {
					keys.get(keys.put(Keys.LEFT, false));
				}
				if (keycode == Input.Keys.RIGHT) {
					keys.get(keys.put(Keys.RIGHT, false));
				}
				if (keycode == Input.Keys.UP) {
					keys.get(keys.put(Keys.UP, false));
				}
				if (keycode == Input.Keys.DOWN) {
					keys.get(keys.put(Keys.DOWN, false));
				}
			}
		});
		eventManager.listen(SwipeEvent.class, new SwipeListener() {
			@Override
			public void swipe(SwipeDirection direction) {
				switch (direction) {
					case UP:
						keys.get(keys.put(Keys.UP, true));
						keys.get(keys.put(Keys.DOWN, false));
						keys.get(keys.put(Keys.LEFT, false));
						keys.get(keys.put(Keys.RIGHT, false));
						break;
					case DOWN:
						keys.get(keys.put(Keys.UP, false));
						keys.get(keys.put(Keys.DOWN, true));
						keys.get(keys.put(Keys.LEFT, false));
						keys.get(keys.put(Keys.RIGHT, false));
						break;
					case LEFT:
						keys.get(keys.put(Keys.UP, false));
						keys.get(keys.put(Keys.DOWN, false));
						keys.get(keys.put(Keys.LEFT, true));
						keys.get(keys.put(Keys.RIGHT, false));
						break;
					case RIGHT:
						keys.get(keys.put(Keys.UP, false));
						keys.get(keys.put(Keys.DOWN, false));
						keys.get(keys.put(Keys.LEFT, false));
						keys.get(keys.put(Keys.RIGHT, true));
						break;
				}
			}
		});
	}

	@Override
	public void update(float delta) {
		if (getEntity().has(AccelerationComponent.class)) {
			Vector2 acceleration = getEntity().get(AccelerationComponent.class).getAcceleration();
			Vector2 velocity = getEntity().get(VelocityComponent.class).getVelocity();

			Vector2 previousVelocity = new Vector2(velocity);
			if (keys.get(Keys.UP) && !keys.get(Keys.DOWN) && !keys.get(Keys.LEFT) && !keys.get(Keys.RIGHT)) {
				velocity.set(0, 0);
				acceleration.set(0, ACCELERATION);
			} else if (!keys.get(Keys.UP) && keys.get(Keys.DOWN) && !keys.get(Keys.LEFT) && !keys.get(Keys.RIGHT)) {
				velocity.set(0, 0);
				acceleration.set(0, -ACCELERATION);
			} else if (!keys.get(Keys.UP) && !keys.get(Keys.DOWN) && keys.get(Keys.LEFT) && !keys.get(Keys.RIGHT)) {
				velocity.set(0, 0);
				acceleration.set(-ACCELERATION, 0);
			} else if (!keys.get(Keys.UP) && !keys.get(Keys.DOWN) && !keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) {
				velocity.set(0, 0);
				acceleration.set(ACCELERATION, 0);
			} else {
				acceleration.set(0, 0);
			}
			updateDrawable(previousVelocity, velocity);
		}
	}

	private void updateDrawable(final Vector2 previousVelocity, final Vector2 velocity) {
		if (getEntity().has(DrawableComponent.class)) {
			DrawableComponent drawableComponent = getEntity().get(DrawableComponent.class);
			TextureComponent textureComponent = null;
			AnimationComponent animationComponent = null;
			if (getEntity().has(TextureComponent.class)) {
				textureComponent = getEntity().get(TextureComponent.class);
			}
			if (getEntity().has(AnimationComponent.class)) {
				animationComponent = getEntity().get(AnimationComponent.class);
			}

			//Player is now Idle
			if (previousVelocity.x == 0 && previousVelocity.y == 0) {
				if (textureComponent != null) {
					try {
						if (velocity.x < 0) {
							drawableComponent.setTextureRegion(textureComponent.get("IdleLeft"));
						} else if (velocity.x > 0) {
							drawableComponent.setTextureRegion(textureComponent.get("IdleRight"));
						} else if (velocity.y > 0) {
							drawableComponent.setTextureRegion(textureComponent.get("IdleUp"));
						} else if (velocity.y < 0) {
							drawableComponent.setTextureRegion(textureComponent.get("IdleDown"));
						}
					} catch (IllegalArgumentException e) {
						drawableComponent.setTextureRegion(new TextureRegion());
					}
				} else {
					drawableComponent.setTextureRegion(new TextureRegion());
				}
				return;
			}

			//Player is moving
			if (animationComponent != null) {
				try {
					if (previousVelocity.x < 0) {
						drawableComponent.setTextureRegion(animationComponent.get("WalkingLeft"));
					} else if (previousVelocity.x > 0) {
						drawableComponent.setTextureRegion(animationComponent.get("WalkingRight"));
					} else if (previousVelocity.y > 0) {
						drawableComponent.setTextureRegion(animationComponent.get("WalkingUp"));
					} else if (previousVelocity.y < 0) {
						drawableComponent.setTextureRegion(animationComponent.get("WalkingDown"));
					}
				} catch (IllegalArgumentException e) {
					drawableComponent.setTextureRegion(new TextureRegion());
				}
			}
		}
	}
}
