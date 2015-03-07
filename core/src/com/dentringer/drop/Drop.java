package com.dentringer.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dentringer.drop.screen.MainMenuScreen;

public class Drop extends Game {

    public SpriteBatch mSpriteBatch;
    public BitmapFont mBitmapFont;

    @Override
    public void create() {
        mSpriteBatch = new SpriteBatch();
        mBitmapFont = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        mSpriteBatch.dispose();
        mBitmapFont.dispose();
    }

}