package com.zarol.projectalias.events;

import com.zarol.projectalias.framework.Event;

/**
 * @author Zarol
 */
public class KeyUpEvent implements Event<KeyUpListener> {
	private int keycode;

	public KeyUpEvent(int keycode) {
		this.keycode = keycode;
	}

	@Override
	public void notify(KeyUpListener listener) {
		listener.keyUp(keycode);
	}
}
