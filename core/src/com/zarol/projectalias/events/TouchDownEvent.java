package com.zarol.projectalias.events;

import com.zarol.projectalias.framework.Event;

/**
 * @author Zarol
 */
public class TouchDownEvent implements Event<TouchDownListener> {
	private int screenX, screenY;
	private int screenWidth, screenHeight;
	private int pointer;
	private int button;

	public TouchDownEvent(int screenX, int screenY, int pointer, int button, int screenWidth, int screenHeight) {
		this.screenX = screenX;
		this.screenY = screenY;
		this.pointer = pointer;
		this.button = button;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}


	@Override
	public void notify(TouchDownListener listener) {
		listener.touchDown(screenX, screenY, pointer, button, screenWidth, screenHeight);
	}
}
