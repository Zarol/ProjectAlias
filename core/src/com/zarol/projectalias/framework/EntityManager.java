package com.zarol.projectalias.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Manages a group of Entities. Provides accessing, removing, and adding Entities.
 *
 * @author Zarol
 */
public class EntityManager {
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Entity> entitiesToAdd = new ArrayList<Entity>();
	private List<Entity> entitiesToRemove = new ArrayList<Entity>();

	/**
	 * Constructor
	 */
	public EntityManager() {
	}

	/**
	 * Return all the managed Entities.
	 *
	 * @return An ArrayList of all the managed Entities.
	 */
	public ArrayList<Entity> getAll() {
		return new ArrayList<Entity>(entities);
	}

	/**
	 * Adds an Entity to be managed on the next update.
	 *
	 * @param entity The Entity to be added.
	 */
	public void add(Entity entity) {
		entity.initialize();
		entitiesToAdd.add(entity);
	}

	/**
	 * Adds a collection of Entities to be managed on the next update.
	 *
	 * @param entites The collection of Entities to be added.
	 */
	public void addAll(Collection<Entity> entites) {
		for (Entity entity : entites) {
			add(entity);
		}
	}

	/**
	 * Removes an Entity on the next update.
	 *
	 * @param entity The Entity to be removed.
	 */
	public void remove(Entity entity) {
		entity.cleanup();
		entitiesToRemove.add(entity);
	}

	/**
	 * Adds and removes Entities that have been queued.
	 */
	public void update() {
		while (!entitiesToAdd.isEmpty()) {
			entities.add(entitiesToAdd.remove(0));
		}
		while (!entitiesToRemove.isEmpty()) {
			entities.remove(entitiesToRemove.remove(0));
		}
	}
}
