package com.zarol.projectalias.framework;

/**
 * Notifies a listener that an Event has occured.
 *
 * @author Zarol
 */
public interface Event<T> {
	/**
	 * Handler for an event listener.
	 *
	 * @param listener The event listener.
	 */
	public void notify(final T listener);
}
