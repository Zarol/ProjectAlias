package com.zarol.projectalias.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.zarol.projectalias.model.Block;
import com.zarol.projectalias.model.Bob;
import com.zarol.projectalias.model.Bob.State;
import com.zarol.projectalias.model.World;

public class WorldRenderer {
	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;

	private World world;
	private OrthographicCamera cam;

	ShapeRenderer debugRenderer = new ShapeRenderer();

	private SpriteBatch spriteBatch;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX;
	private float ppuY;

	private static final float RUNNING_FRAME_DURATION = 0.06f;

	private TextureRegion blockTexture;

	private TextureRegion bobIdleLeft;
	private TextureRegion bobIdleRight;
	private TextureRegion bobJumpLeft;
	private TextureRegion bobFallLeft;
	private TextureRegion bobJumpRight;
	private TextureRegion bobFallRight;
	private TextureRegion bobFrame;

	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;

	public WorldRenderer(World world, boolean debug) {
		this.world = world;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		this.debug = debug;
		spriteBatch = new SpriteBatch();
		loadTextures();
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		ppuX = (float) this.width / CAMERA_WIDTH;
		ppuY = (float) this.height / CAMERA_HEIGHT;
	}

	public void render() {
		spriteBatch.begin();
		drawBlocks();
		drawBob();
		spriteBatch.end();

		drawCollisionBlocks();

		if (debug) {
			drawDebug();
		}
	}

	private void loadTextures() {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/textures/textures.atlas"));

		blockTexture = atlas.findRegion("block");

		bobIdleLeft = atlas.findRegion("bob", 1);
		bobIdleRight = new TextureRegion(bobIdleLeft);
		bobIdleRight.flip(true, false);
		TextureRegion[] walkLeftFrames = new TextureRegion[5];
		for (int i = 0, j = 2; i < 5; i++, j++) {
			walkLeftFrames[i] = atlas.findRegion("bob", j);
		}
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);

		TextureRegion[] walkRightFrames = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
			walkRightFrames[i].flip(true, false);
		}
		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);

		bobJumpLeft = atlas.findRegion("bob-up");
		bobJumpRight = new TextureRegion(bobJumpLeft);
		bobJumpRight.flip(true, false);
		bobFallLeft = atlas.findRegion("bob-down");
		bobFallRight = new TextureRegion(bobFallLeft);
		bobFallRight.flip(true, false);
	}

	private void drawBlocks() {
		for (Block block : world.getDrawableBlocks((int) CAMERA_WIDTH, (int) CAMERA_HEIGHT)) {
			spriteBatch.draw(blockTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY,
					Block.SIZE * ppuX, Block.SIZE * ppuY);
		}
	}

	private void drawBob() {
		Bob bob = world.getBob();
		bobFrame = bob.isFacingLeft() ? bobIdleLeft : bobIdleRight;
		if (bob.getState().equals(State.WALKING)) {
			bobFrame = bob.isFacingLeft() ? walkLeftAnimation.getKeyFrame(bob.getStateTime(), true)
					: walkRightAnimation.getKeyFrame(bob.getStateTime(), true);
		} else if (bob.getState().equals(State.JUMPING)) {
			if (bob.getVelocity().y > 0) {
				bobFrame = bob.isFacingLeft() ? bobJumpLeft : bobJumpRight;
			} else {
				bobFrame = bob.isFacingLeft() ? bobFallLeft : bobFallRight;
			}
		}
		spriteBatch.draw(bobFrame, bob.getPosition().x * ppuX, bob.getPosition().y * ppuY, Bob.SIZE * ppuX,
				Bob.SIZE * ppuY);
	}

	private void drawCollisionBlocks() {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Filled);
		debugRenderer.setColor(new Color(1, 1, 1, 1));

		for (Rectangle rectangle : world.getCollionRectangles()) {
			debugRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		}
		debugRenderer.end();
	}

	private void drawDebug() {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Line);

		// Render Blocks
		{
			for (Block block : world.getDrawableBlocks((int) CAMERA_WIDTH, (int) CAMERA_HEIGHT)) {
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
			debugRenderer.setColor(new Color(0, 1, 0, 1));
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}

		debugRenderer.end();
	}
}
