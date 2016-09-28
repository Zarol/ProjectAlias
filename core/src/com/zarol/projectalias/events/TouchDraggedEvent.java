package com.zarol.projectalias.events;

import com.zarol.projectalias.framework.Event;

/**
 * @author Zarol
 */
public class TouchDraggedEvent implements Event<TouchDraggedListener> {
	private int screenX, screenY;
	private int pointer;

	public TouchDraggedEvent(int screenX, int screenY, int pointer) {
		this.screenX = screenX;
		this.screenY = screenY;
		this.pointer = pointer;
	}

	@Override
	public void notify(TouchDraggedListener listener) {
		listener.touchDragged(screenX, screenY, pointer);
	}
}
