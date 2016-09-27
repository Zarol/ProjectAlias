package com.zarol.projectalias.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages a group of listeners subscribed to certain events. Provides subscription and unsubscription.
 *
 * @author Zarol
 */
public final class EventManager {
	private final Map<Class, Collection> eventListenerMap = new HashMap<Class, Collection>(10);

	/**
	 * Add a listener to an event class.
	 *
	 * @param eventClass The event class to listen to.
	 * @param listener   The class that wants to listen.
	 * @param <T>        The type of listener.
	 */
	public <T> void listen(Class<? extends Event<T>> eventClass, T listener) {
		final Collection<T> listeners = listenersOf(eventClass);
		synchronized (listeners) {
			if (!listeners.contains(listener)) {
				listeners.add(listener);
			}
		}
	}

	/**
	 * Stops the sending of an event class to a given listener.
	 *
	 * @param eventClass The event class to stop sending.
	 * @param listener   The listener to stop sending to.
	 * @param <T>        The type of listener.
	 */
	public <T> void mute(Class<? extends Event<T>> eventClass, T listener) {
		final Collection<T> listeners = listenersOf(eventClass);
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	/**
	 * Notifies a new event to registered listeners of the event class.
	 *
	 * @param event The event to notify listeners about.
	 * @param <T>   The type of Event.
	 */
	public <T> void notify(final Event<T> event) {
		@SuppressWarnings("unchecked")
		Class<Event<T>> eventClass = (Class<Event<T>>) event.getClass();

		for (T listener : listenersOf(eventClass)) {
			event.notify(listener);
		}
	}

	/**
	 * Gets the listeners for a given event class.
	 *
	 * @param eventClass The event class to get the listeners for.
	 * @param <T>        The type of Event.
	 * @return The collection of listeners subscribed to the event class.
	 */
	private <T> Collection<T> listenersOf(Class<? extends Event<T>> eventClass) {
		synchronized (eventListenerMap) {
			@SuppressWarnings("unchecked")
			final Collection<T> listeners = eventListenerMap.get(eventClass);
			if (listeners != null) {
				return listeners;
			}

			final Collection<T> emptyList = new ArrayList<T>(5);
			eventListenerMap.put(eventClass, emptyList);
			return emptyList;
		}
	}
}
