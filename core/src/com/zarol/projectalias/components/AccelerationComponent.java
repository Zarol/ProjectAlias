package com.zarol.projectalias.components;

import com.badlogic.gdx.math.Vector2;
import com.zarol.projectalias.framework.Component;

/**
 * @author Zarol
 */
public class AccelerationComponent extends Component {
	private Vector2 accelerate;
	private Vector2 acceleration;

	public AccelerationComponent() {
		accelerate = new Vector2();
		acceleration = new Vector2();
	}

	public AccelerationComponent(Vector2 accelerate) {
		this.accelerate = accelerate;
		acceleration = new Vector2();
	}

	public AccelerationComponent(Vector2 accelerate, Vector2 acceleration) {
		this.accelerate = accelerate;
		this.acceleration = acceleration;
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	public Vector2 getAccelerate() {
		return accelerate;
	}

	public void setAccelerate(Vector2 accelerate) {
		this.accelerate = accelerate;
	}
}
