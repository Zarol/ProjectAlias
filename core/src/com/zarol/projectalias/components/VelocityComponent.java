package com.zarol.projectalias.components;

import com.badlogic.gdx.math.Vector2;
import com.zarol.projectalias.framework.Component;

/**
 * @author Zarol
 */
public class VelocityComponent extends Component {
	private Vector2 velocity;
	private Vector2 maxVelocity;
	private Vector2 dampen;

	public VelocityComponent() {
		velocity = new Vector2();
		maxVelocity = new Vector2(Float.MAX_VALUE, Float.MAX_VALUE);
		dampen = new Vector2(0f, 1f);
	}

	public VelocityComponent(Vector2 maxVelocity) {
		velocity = new Vector2();
		this.maxVelocity = maxVelocity;
		dampen = new Vector2(0f, 1f);
	}

	public VelocityComponent(Vector2 maxVelocity, Vector2 velocity) {
		this.velocity = velocity;
		this.maxVelocity = maxVelocity;
		dampen = new Vector2(0f, 1f);
	}

	public VelocityComponent(Vector2 maxVelocity, Vector2 velocity, Vector2 dampen) {
		this.velocity = velocity;
		this.maxVelocity = maxVelocity;
		this.dampen = dampen;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public Vector2 getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(Vector2 maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	public Vector2 getDampen() {
		return dampen;
	}

	public void setDampen(Vector2 dampen) {
		this.dampen = dampen;
	}
}
