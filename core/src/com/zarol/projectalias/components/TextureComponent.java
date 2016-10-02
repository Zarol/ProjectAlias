package com.zarol.projectalias.components;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.zarol.projectalias.framework.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zarol
 */
public class TextureComponent extends Component {
	private Map<String, TextureRegion> textures;

	public TextureComponent() {
		textures = new HashMap<String, TextureRegion>();
	}

	public void loadTexture(String textureAlias, TextureAtlas textureAtlas, String regionName) {
		textures.put(textureAlias, new TextureRegion(textureAtlas.findRegion(regionName)));
	}

	public void loadTexture(String textureAlias, TextureAtlas textureAtlas, String regionName,
							boolean flipX, boolean flipY) {
		TextureRegion textureRegion = new TextureRegion(textureAtlas.findRegion(regionName));
		textureRegion.flip(flipX, flipY);
		textures.put(textureAlias, textureRegion);
	}

	public void loadTexture(String textureAlias, TextureAtlas textureAtlas, String regionName, int index) {
		textures.put(textureAlias, new TextureRegion(textureAtlas.findRegion(regionName, index)));
	}

	public void loadTexture(String textureAlias, TextureAtlas textureAtlas, String regionName, int index,
							boolean flipX, boolean flipY) {
		TextureRegion textureRegion = new TextureRegion(textureAtlas.findRegion(regionName, index));
		textureRegion.flip(flipX, flipY);
		textures.put(textureAlias, textureRegion);
	}

	public boolean has(String textureAlias) {
		return textures.containsKey(textureAlias);
	}

	public TextureRegion get(String textureAlias) {
		if (!has(textureAlias)) {
			throw new IllegalArgumentException(getEntity().getClass().getName() +
					" does not contain " + textureAlias + ".");
		}
		return textures.get(textureAlias);
	}
}
