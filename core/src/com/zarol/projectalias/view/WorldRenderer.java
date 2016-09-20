package com.zarol.projectalias.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.zarol.projectalias.model.Block;
import com.zarol.projectalias.model.Bob;
import com.zarol.projectalias.model.World;

public class WorldRenderer {
	private World world;
	private OrthographicCamera cam;

	ShapeRenderer debugRenderer = new ShapeRenderer();

	public WorldRenderer(World world) {
		this.world = world;
		this.cam = new OrthographicCamera(10, 7);
		this.cam.position.set(5, 3.5f, 0);
		this.cam.update();
	}

	public void render() {
		// Render Blocks
		{
			debugRenderer.setProjectionMatrix(cam.combined);
			debugRenderer.begin(ShapeType.Line);
			for (Block block : world.getBlocks()) {
				Rectangle rect = block.getBounds();
				float x1 = block.getPosition().x + rect.x;
				float y1 = block.getPosition().y + rect.y;
				debugRenderer.setColor(new Color(1, 0, 0, 1));
				debugRenderer.rect(x1, y1, rect.width, rect.height);
			}
		}

		// Render Bob
		{
			Bob bob = world.getBob();
			Rectangle rect = bob.getBounds();
			float x1 = bob.getPosition().x + rect.x;
			float y1 = bob.getPosition().y + rect.y;
			debugRenderer.setColor(new Color(0, 1, 0, 1));
			debugRenderer.rect(x1, y1, rect.width, rect.height);
			debugRenderer.end();
		}
	}
}
