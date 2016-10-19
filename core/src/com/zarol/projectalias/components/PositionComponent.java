package com.zarol.projectalias.components;

import com.badlogic.gdx.math.Vector2;
import com.zarol.projectalias.framework.Component;

/**
 * @author Zarol
 */
public class PositionComponent extends Component {
	private Vector2 position = new Vector2();

	public PositionComponent(Vector2 position) {
		this.position = position;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
}
