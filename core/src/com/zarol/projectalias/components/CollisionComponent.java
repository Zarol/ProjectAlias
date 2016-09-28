package com.zarol.projectalias.components;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.zarol.projectalias.framework.Component;

/**
 * @author Zarol
 */
public class CollisionComponent extends Component {
	private Rectangle bounds;

	public CollisionComponent() {
		bounds = new Rectangle();
	}

	public CollisionComponent(Rectangle bounds) {
		this.bounds = bounds;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	@Override
	public void initialize() {
		if (getEntity().has(PositionComponent.class)) {
			Vector2 position = getEntity().get(PositionComponent.class).getPosition();
			bounds.x = position.x;
			bounds.y = position.y;
		}
	}

	@Override
	public void update(float delta) {
		if (getEntity().has(PositionComponent.class)) {
			Vector2 position = getEntity().get(PositionComponent.class).getPosition();
			bounds.x = position.x;
			bounds.y = position.y;
		}
	}
}
