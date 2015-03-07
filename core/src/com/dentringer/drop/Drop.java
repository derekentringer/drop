package com.dentringer.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.awt.Rectangle;

public class Drop extends ApplicationAdapter {

    private Texture mDropImage;
    private Texture mBucketImage;
    private Sound mDropSound;
    private Music mDropMusic;

    private OrthographicCamera mDropCamera;
    private SpriteBatch mSpriteBatch;

    private Rectangle mRectangle;

    private Vector3 mTouchPosition;
	
	@Override
	public void create () {
        mDropImage = new Texture(Gdx.files.internal("droplet.png"));
        mBucketImage = new Texture(Gdx.files.internal("bucket.png"));
        mDropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        mDropMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        mDropMusic.setLooping(true);
        mDropMusic.play();

        mDropCamera = new OrthographicCamera();
        mDropCamera.setToOrtho(false, 800, 480);

        mSpriteBatch = new SpriteBatch();

        mRectangle = new Rectangle();
        mRectangle.x = 800/2-64/2;
        mRectangle.y = 20;
        mRectangle.width = 64;
        mRectangle.height = 64;
	}

	@Override
	public void render () {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mDropCamera.update();

        mSpriteBatch.setProjectionMatrix(mDropCamera.combined);
        mSpriteBatch.begin();
        mSpriteBatch.draw(mBucketImage, mRectangle.x, mRectangle.y);
        mSpriteBatch.end();

        if(Gdx.input.isTouched()) {
            mTouchPosition = new Vector3();
            mTouchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            mDropCamera.unproject(mTouchPosition);
            mRectangle.x = (int)mTouchPosition.x - 64/2;
        }
	}
}
