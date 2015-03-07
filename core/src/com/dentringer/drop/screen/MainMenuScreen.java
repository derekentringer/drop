package com.dentringer.drop.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dentringer.drop.Drop;

public class MainMenuScreen implements Screen {

    public final Drop mDropGame;
    public OrthographicCamera mOrthoCamera;

    public MainMenuScreen(final Drop game) {
        mDropGame = game;
        mOrthoCamera = new OrthographicCamera();
        mOrthoCamera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mOrthoCamera.update();
        mDropGame.mSpriteBatch.setProjectionMatrix(mOrthoCamera.combined);

        mDropGame.mSpriteBatch.begin();
        mDropGame.mBitmapFont.draw(mDropGame.mSpriteBatch, "Welcome to Drop!!! ", 100, 150);
        mDropGame.mBitmapFont.draw(mDropGame.mSpriteBatch, "Tap anywhere to begin!", 100, 100);
        mDropGame.mSpriteBatch.end();

        if (Gdx.input.isTouched()) {
            mDropGame.setScreen(new GameScreen(mDropGame));
            dispose();
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

    }

}