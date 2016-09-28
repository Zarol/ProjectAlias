package com.zarol.projectalias.events;

import com.zarol.projectalias.framework.Event;

/**
 * @author Zarol
 */
public class TouchUpEvent implements Event<TouchUpListener> {
	private int screenX, screenY;
	private int screenWidth, screenHeight;
	private int pointer;
	private int button;

	public TouchUpEvent(int screenX, int screenY, int pointer, int button, int screenWidth, int screenHeight) {
		this.screenX = screenX;
		this.screenY = screenY;
		this.pointer = pointer;
		this.button = button;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	@Override
	public void notify(TouchUpListener listener) {
		listener.touchUp(screenX, screenY, pointer, button, screenWidth, screenHeight);
	}
}
