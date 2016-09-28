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

	public void loadTexture(TextureAtlas atlas, String regionName, int index, String textureAlias) {
		textures.put(textureAlias, atlas.findRegion(regionName, index));
	}

	public void loadTexture(TextureRegion textureRegion, String textureAlias) {
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
