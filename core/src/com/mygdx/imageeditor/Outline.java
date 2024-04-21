package com.mygdx.imageeditor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Outline {
	public Texture OutlineTex;

	public Outline(Vector2 scale, Color color, int thickness) {
		Pixmap map = new Pixmap((int) scale.x, (int) scale.y, Format.RGBA8888);
		map.setColor(color);
		OutlineTex = new Texture(map);
		// Top
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < thickness; y++) {
				map.drawPixel(x, y);
			}
		}
		//Bottom side
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = map.getHeight() - thickness; y < map.getHeight(); y++) {
				map.drawPixel(x, y);
			}
		}

		//Left side
		for (int x = 0; x < thickness; x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				map.drawPixel(x, y);
			}
		}

		//Right side
		for (int x = map.getWidth() - thickness; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				map.drawPixel(x, y);
			}
		}
		map.dispose();

	}

}
