package com.zarol.projectalias.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bob {
	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}

	public static final float SIZE = 0.5f;

	Vector2 position = new Vector2();
	Vector2 acceleration = new Vector2();
	Vector2 velocity = new Vector2();
	Vector2 velocityFrame = new Vector2();
	Rectangle bounds = new Rectangle();
	State state = State.IDLE;
	float stateTime = 0;
	boolean facingLeft = true;
	boolean longJump = false;

	public Bob(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}

	public void update(float delta) {
		stateTime += delta;
		velocityFrame.set(velocity).scl(delta);
		position.add(velocityFrame);
		bounds.x = position.x;
		bounds.y = position.y;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
		this.bounds.setX(this.position.x);
		this.bounds.setY(this.position.y);
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public float getStateTime() {
		return stateTime;
	}

	public boolean isFacingLeft() {
		return facingLeft;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}

	public boolean isLongJump() {
		return longJump;
	}

	public void setLongJump(boolean longJump) {
		this.longJump = longJump;
	}
}
