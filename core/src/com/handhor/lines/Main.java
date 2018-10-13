package com.handhor.lines;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Main extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera cam;
	LinesGame lines;

	@Override
	public void create () {
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Const.WIDTH, Const.HEIGHT);
		batch.setProjectionMatrix(cam.combined);

		lines = new LinesGame();
		lines.init();
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		lines.update(Gdx.graphics.getDeltaTime());
		lines.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}

