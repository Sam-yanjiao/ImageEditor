package com.mygdx.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class ExitButton extends Button {
	public ExitButton(Vector2 scale, Vector2 position, Color recColor) {
		super(scale, position, recColor);
		ButtonText = "Exit";
	}

	public void onClickUp(Vector2 clickPosition) {
		super.onClickUp(clickPosition);
		Gdx.app.exit();
		System.exit(-1);
	}
}
