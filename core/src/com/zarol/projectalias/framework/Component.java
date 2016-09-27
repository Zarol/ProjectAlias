package com.zarol.projectalias.framework;

/**
 * Defines a state and partial functionality for an Entity.
 *
 * @author Zarol
 * @see Entity
 */
public abstract class Component {
	private boolean isActive = true;
	protected Entity entity;

	/**
	 * The current active state of the Component.
	 *
	 * @return True if the Component will be updated, false otherwise.
	 */
	public final boolean isActive() {
		return isActive;
	}

	/**
	 * Returns the Entity this Component is attached to.
	 *
	 * @return The Entity this Component is attached to.
	 */
	public final Entity getEntity() {
		return entity;
	}

	/**
	 * Sets the Entity this Component is attached to.
	 *
	 * @param entity The Entity this Component is attached to.
	 */
	public final void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * Component initialization logic.
	 */
	public void initialize() {
	}

	/**
	 * Component cleanup logic.
	 */
	public void cleanup() {

	}

	/**
	 * Component update logic.
	 *
	 * @param delta The Time that has passed since the last update.
	 */
	@SuppressWarnings("unused")
	public void update(float delta) {
	}
}
