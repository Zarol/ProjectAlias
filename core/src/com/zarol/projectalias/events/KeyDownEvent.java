package com.zarol.projectalias.events;

import com.zarol.projectalias.framework.Event;

/**
 * @author Zarol
 */
public class KeyDownEvent implements Event<KeyDownListener> {
	private int keycode;

	public KeyDownEvent(int keycode) {
		this.keycode = keycode;
	}

	@Override
	public void notify(KeyDownListener listener) {
		listener.keyDown(keycode);
	}
}
