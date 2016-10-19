package com.zarol.projectalias.systems;

import com.zarol.projectalias.framework.EntityManager;
import com.zarol.projectalias.framework.EventManager;

/**
 * Defines a System which acts on Entities based on the contents of its Components.
 *
 * @author Zarol
 * @see com.zarol.projectalias.framework.Component
 * @see com.zarol.projectalias.framework.Entity
 */
public abstract class EntitySystem {
	private boolean isActive = true;
	protected EntityManager entityManager;
	protected EventManager eventManager;

	/**
	 * The current active state of the EntitySystem.
	 *
	 * @return True if the EntitySystem will be updated, false otherwise.
	 */
	public final boolean isActive() {
		return isActive;
	}

	/**
	 * EntitySystem initialization logic.
	 */
	public void initialize() {
	}

	/**
	 * EntitySystem cleanup logic.
	 */
	public void cleanup() {
	}

	/**
	 * EntitySystem update logic.
	 *
	 * @param delta The Time that has passed since the last update.
	 */
	public void update(float delta) {
	}
}
