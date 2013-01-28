package com.beegroove.turrets;

import com.badlogic.gdx.Screen;

public abstract class GenericScreen implements Screen {
	public abstract void update (float delta);

	/** Called when a screen should render itself */
	public abstract void draw (float delta);

	public abstract boolean isDone ();

	@Override
	public void render (float delta) {
		update(delta);
		draw(delta);
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void show () {
	}

	@Override
	public void hide () {
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
}
