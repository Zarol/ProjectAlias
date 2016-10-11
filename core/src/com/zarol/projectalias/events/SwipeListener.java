package com.zarol.projectalias.events;

/**
 * @author Zarol
 */
public interface SwipeListener {
	enum SwipeDirection {
		UP, DOWN, LEFT, RIGHT
	}

	void swipe(final SwipeDirection direction);
}
