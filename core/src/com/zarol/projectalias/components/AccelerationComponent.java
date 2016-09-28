package com.zarol.projectalias.components;

import com.badlogic.gdx.math.Vector2;
import com.zarol.projectalias.framework.Component;

/**
 * @author Zarol
 */
public class AccelerationComponent extends Component {
	private Vector2 acceleration = new Vector2();

	public AccelerationComponent(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	@Override
	public void update(float delta) {
		acceleration.scl(delta);
	}
}
