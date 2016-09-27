package com.zarol.projectalias.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides functionality and state for an Entity composed of Components.
 * An Entity may only have one type of each Component attached.
 *
 * @author Zarol
 * @see Component
 */
public class Entity {
	private boolean isInitialized = false;
	private boolean isActive = false;
	private Map<Class<? extends Component>, Component> componentMap =
			new HashMap<Class<? extends Component>, Component>();
	private List<Component> componentsToAdd = new ArrayList<Component>();
	private List<Class<? extends Component>> componentsToRemove = new ArrayList<Class<? extends Component>>();

	/**
	 * The current active state of the Entity.
	 *
	 * @return True if the Entity will be updated, false otherwise.
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * Sets the Entity to be active (updating) or inactive (non-updating).
	 *
	 * @param isActive True to set the Entity active, false otherwise.
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * Determines if the Entity is composed of a given Component.
	 *
	 * @param componentClass The class of the Component to check.
	 * @param <T>            The type of Component.
	 * @return True if the Component of type T is attached to the Entity, false otherwise.
	 */
	public <T extends Component> boolean has(Class<T> componentClass) {
		return componentMap.containsKey(componentClass);
	}

	/**
	 * Returns the Component if it attached to the Entity.
	 *
	 * @param componentClass The class of the Component to get.
	 * @param <T>            The type of Component.
	 * @return The Component attached to the Entity of type T.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> T get(Class<T> componentClass) {
		if (!has(componentClass)) {
			throw new IllegalArgumentException(componentClass.getName() + " could not be found attached to " +
					this.getClass().getName());
		}
		return (T) componentMap.get(componentClass);
	}

	/**
	 * Attaches a Component to the Entity.
	 *
	 * @param component The Component to attach.
	 */
	public void attach(Component component) {
		if (has(component.getClass())) {
			throw new IllegalArgumentException(component.getClass().getName() + " already exists attached to " +
					this.getClass().getName());
		}
		componentMap.put(component.getClass(), component);
		component.setEntity(this);
		if (isInitialized) {
			component.initialize();
		}
	}

	/**
	 * Detaches a Component of type T from the Entity if it exists.
	 *
	 * @param componentClass The class of the Component to detach.
	 * @param <T>            The type of Component.
	 */
	public <T extends Component> void detach(Class<T> componentClass) {
		if (has(componentClass) && !componentsToRemove.contains(componentClass)) {
			componentsToRemove.add(componentClass);
		}
	}

	/**
	 * If the Component of the same type already exists, remove the existing Component and add the new Component.
	 *
	 * @param component The new Component to replace the existing Component.
	 */
	public void replace(Component component) {
		if (has(component.getClass())) {
			detach(component.getClass());
		}
		if (isInitialized) {
			componentsToAdd.add(component);
		} else {
			attach(component);
		}
	}

	/**
	 * Sets the Entity to be active and initializes all attached Components.
	 */
	public void initialize() {
		isInitialized = true;
		isActive = true;
		for (Component component : componentMap.values()) {
			component.initialize();
		}
	}

	/**
	 * Sets the Entity to be inactive and cleans up all attached Components.
	 */
	public void cleanup() {
		isActive = false;
		for (Component component : componentMap.values()) {
			component.cleanup();
		}
	}

	/**
	 * Updates all attached Components. Removes all detached Components. Attaches newly attached Components.
	 *
	 * @param delta The Time that has passed since the last update.
	 */
	public void update(float delta) {
		for (Component component : componentMap.values()) {
			if (component.isActive()) {
				component.update(delta);
			}
		}
		while (!componentsToRemove.isEmpty()) {
			remove(componentsToRemove.remove(0));
		}
		while (!componentsToAdd.isEmpty()) {
			attach(componentsToAdd.remove(0));
		}
	}

	/**
	 * Removes the Component from the Entity entirely after cleaning up the Component.
	 *
	 * @param componentClass The class of the Component to remove.
	 * @param <T>            The type of Component.
	 */
	private <T extends Component> void remove(Class<T> componentClass) {
		if (!has(componentClass)) {
			throw new IllegalArgumentException(componentClass.getName() + " could not be found attached to " +
					this.getClass().getName());
		}
		componentMap.get(componentClass).cleanup();
		componentMap.remove(componentClass);
	}
}
