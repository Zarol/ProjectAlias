package com.zarol.projectalias.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.zarol.projectalias.framework.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zarol
 */
public class AnimationComponent extends Component {
	private float stateTime;
	private Map<String, Animation> animations;

	public AnimationComponent() {
		stateTime = 0f;
		animations = new HashMap<String, Animation>();
	}

	public void loadAnimation(TextureAtlas atlas, String regionName,
							  int start, int end, float duration, String animationAlias) {
		if (end > start) {
			throw new IllegalArgumentException("The end value cannot be larger than the starting value.");
		}
		TextureRegion[] animationFrames = new TextureRegion[end - start];
		for (int i = 0, j = start; j < end; ++i, ++j) {
			animationFrames[i] = atlas.findRegion(regionName, j);
		}
		animations.put(animationAlias, new Animation(duration, animationFrames));
	}

	public void loadAnimation(TextureAtlas atlas, String regionName,
							  int start, int end, float duration, String animationAlias,
							  boolean flipX, boolean flipY) {
		if (end > start) {
			throw new IllegalArgumentException("The end value cannot be larger than the starting value.");
		}
		TextureRegion[] animationFrames = new TextureRegion[end - start];
		for (int i = 0, j = start; j < end; ++i, ++j) {
			animationFrames[i] = atlas.findRegion(regionName, j);
			animationFrames[i].flip(flipX, flipY);
		}
		animations.put(animationAlias, new Animation(duration, animationFrames));
	}

	public boolean has(String textureAlias) {
		return animations.containsKey(textureAlias);
	}

	public TextureRegion get(String textureAlias) {
		if (!has(textureAlias)) {
			throw new IllegalArgumentException(getEntity().getClass().getName() +
					" does not contain " + textureAlias + " in its AnimationComponent.");
		}
		return animations.get(textureAlias).getKeyFrame(stateTime, true);
	}

	public TextureRegion get(String textureAlias, boolean looping) {
		if (!has(textureAlias)) {
			throw new IllegalArgumentException(getEntity().getClass().getName() +
					" does not contain " + textureAlias + " in its AnimationComponent.");
		}
		return animations.get(textureAlias).getKeyFrame(stateTime, looping);
	}

	@Override
	public void update(float delta) {
		stateTime += delta;
	}
}
