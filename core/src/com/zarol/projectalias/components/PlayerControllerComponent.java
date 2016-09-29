package com.zarol.projectalias.components;

import com.badlogic.gdx.Input;
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

	private static final float ACCELERATION = 20f;

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
		eventManager.listen(TouchDownEvent.class, new TouchDownListener() {
			@Override
			public void touchDown(int screenX, int screenY, int pointer, int button,
								  int screenWidth, int screenHeight) {
				if (screenX < screenWidth / 2 && screenY > screenHeight / 2) {
					keys.get(keys.put(Keys.LEFT, true));
				}
				if (screenX > screenWidth / 2 && screenY > screenHeight / 2) {
					keys.get(keys.put(Keys.RIGHT, true));
				}
				if (screenY < screenHeight / 2) {
					keys.get(keys.put(Keys.UP, true));
				}
			}
		});
		eventManager.listen(TouchUpEvent.class, new TouchUpListener() {
			@Override
			public void touchUp(int screenX, int screenY, int pointer, int button, int screenWidth, int screenHeight) {
				if (screenX < screenWidth / 2 && screenY > screenHeight / 2) {
					keys.get(keys.put(Keys.LEFT, false));
				}
				if (screenX > screenWidth / 2 && screenY > screenHeight / 2) {
					keys.get(keys.put(Keys.RIGHT, false));
				}
				if (screenY < screenHeight / 2) {
					keys.get(keys.put(Keys.UP, false));
				}
			}
		});
	}

	@Override
	public void update(float delta) {
		if (getEntity().has(AccelerationComponent.class)) {
			Vector2 acceleration = getEntity().get(AccelerationComponent.class).getAcceleration();

			if (keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) {
				acceleration.x = 0;
			} else if (keys.get(Keys.LEFT)) {
				acceleration.x = -ACCELERATION;
			} else if (keys.get(Keys.RIGHT)) {
				acceleration.x = ACCELERATION;
			} else {
				acceleration.x = 0;
			}
		}
	}
}
