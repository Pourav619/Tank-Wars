package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Drop extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
	public static final int V_WIDTH= 500;
	public static final int V_HEIGHT=100;
	public static final float PPM = 100;
	public Viewport screenPort;

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		screenPort= new StretchViewport(Drop.V_WIDTH*2,Drop.V_HEIGHT/4,new OrthographicCamera());
		this.setScreen(new MainMenu(this));
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
