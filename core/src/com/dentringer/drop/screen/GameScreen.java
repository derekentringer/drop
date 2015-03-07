package com.dentringer.drop.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.dentringer.drop.Drop;

import java.util.Iterator;

public class GameScreen implements Screen {

    public final Drop mDropGame;

    private Texture mDropImage;
    private Texture mBucketImage;
    private Sound mDropSound;
    private Music mDropMusic;
    private int mDropsGathered;
    private Array<Rectangle> mRainDrops;
    private long mLastDropTime;
    private OrthographicCamera mDropCamera;
    private Rectangle mRectangle;
    private Vector3 mTouchPosition;

	public GameScreen (final Drop dropGame) {
        this.mDropGame = dropGame;

        mDropImage = new Texture(Gdx.files.internal("droplet.png"));
        mBucketImage = new Texture(Gdx.files.internal("bucket.png"));
        //TODO wav should be 16bit
        mDropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        mDropMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        mDropMusic.setLooping(true);
        mDropMusic.play();

        mDropCamera = new OrthographicCamera();
        mDropCamera.setToOrtho(false, 800, 480);

        mRectangle = new Rectangle();
        mRectangle.x = 800/2-64/2;
        mRectangle.y = 20;
        mRectangle.width = 64;
        mRectangle.height = 64;

        mRainDrops = new Array<Rectangle>();
        spawnRainDrop();
	}

    private void spawnRainDrop() {
        Rectangle mRainDrop = new Rectangle();
        mRainDrop.x = MathUtils.random(0, 800-64);
        mRainDrop.y = 480;
        mRainDrop.width = 64;
        mRainDrop.height = 64;
        mRainDrops.add(mRainDrop);
        mLastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        mDropCamera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        mDropGame.mSpriteBatch.setProjectionMatrix(mDropCamera.combined);

        // begin a new batch and draw the bucket and all drops
        mDropGame.mSpriteBatch.begin();
        mDropGame.mBitmapFont.draw(mDropGame.mSpriteBatch, "Drops Collected: " + mDropsGathered, 0, 480);
        mDropGame.mSpriteBatch.draw(mBucketImage, mRectangle.x, mRectangle.y);
        for (Rectangle raindrop : mRainDrops) {
            mDropGame.mSpriteBatch.draw(mDropImage, raindrop.x, raindrop.y);
        }
        mDropGame.mSpriteBatch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            mTouchPosition = new Vector3();
            mTouchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            mDropCamera.unproject(mTouchPosition);
            mRectangle.x = mTouchPosition.x - 64 / 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            mRectangle.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            mRectangle.x += 200 * Gdx.graphics.getDeltaTime();
        }

        // make sure the bucket stays within the screen bounds
        if (mRectangle.x < 0) {
            mRectangle.x = 0;
        }
        if (mRectangle.x > 800 - 64) {
            mRectangle.x = 800 - 64;
        }

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - mLastDropTime > 1000000000) {
            spawnRainDrop();
        }

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        Iterator<Rectangle> iter = mRainDrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0) {
                iter.remove();
            }
            if (raindrop.overlaps(mRectangle)) {
                mDropsGathered++;
                mDropSound.play();
                iter.remove();
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mDropImage.dispose();
        mBucketImage.dispose();
        mDropSound.dispose();
        mDropMusic.dispose();
    }

}