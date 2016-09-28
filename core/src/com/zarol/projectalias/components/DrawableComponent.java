package com.zarol.projectalias.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.zarol.projectalias.framework.Component;

/**
 * @author Zarol
 */
public class DrawableComponent extends Component {
	private TextureRegion textureRegion;
	private float width, height;

	public DrawableComponent(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
		width = 1f;
		height = 1f;
	}

	public DrawableComponent(TextureRegion textureRegion, float width, float height) {
		this.textureRegion = textureRegion;
		this.width = width;
		this.height = height;
	}

	public TextureRegion getTextureRegion() {
		return textureRegion;
	}

	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
}
