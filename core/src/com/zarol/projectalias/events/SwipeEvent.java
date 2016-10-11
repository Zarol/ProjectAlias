package com.zarol.projectalias.events;

import com.zarol.projectalias.framework.Event;

/**
 * @author Zarol
 */
public class SwipeEvent implements Event<SwipeListener> {
	private SwipeListener.SwipeDirection direction;

	public SwipeEvent(SwipeListener.SwipeDirection direction) {
		this.direction = direction;
	}

	@Override
	public void notify(SwipeListener listener) {
		listener.swipe(direction);
	}
}
