package com.mygdx.imageeditor;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.buttons.Button;
import com.mygdx.buttons.ClearDoodleButton;
import com.mygdx.buttons.ColorButton;
import com.mygdx.buttons.ExitButton;
import com.mygdx.buttons.SaveButton;

import Utility.CollisionManager;
import Utility.ImageInputOutput;
import Utility.InputManager;

public class ImageEditor extends ApplicationAdapter {
	SpriteBatch batch;
	public Vector2 ScreenSize;
	public static ImageEditor Instance;
	private EditWindow _editWindow;
	public Array<Rec2D> Rectangles = new Array<Rec2D>();
	

	public void create() {
		Instance = this;
		initializeUtilityClasses();
		createGraphicalElemnets();
	}
	private BitmapFont _font;
	private Rec2D rec;
	private void initializeUtilityClasses() {
		new CollisionManager();
		new ImageInputOutput();
		InputManager inputManager = new InputManager();
		Gdx.input.setInputProcessor(inputManager);
		_font = new BitmapFont();

	}
	private void createGraphicalElemnets() {
		ScreenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Vector2 editWindowSize = new Vector2(500, ScreenSize.y - 25);
		ColorButton button = new ColorButton(new Vector2(50,50), Vector2.Zero, Color.GOLD);
		new ColorButton(new Vector2(50, 50), new Vector2(42, 0), Color.ORANGE);
		new ColorButton(new Vector2(50, 50), new Vector2(0, 51), Color.GREEN);
		new ColorButton(new Vector2(50, 50), new Vector2(42, 51), Color.BLUE);
		new ColorButton(new Vector2(50, 50), new Vector2(0, 101), Color.PINK);
		new ColorButton(new Vector2(50, 50), new Vector2(42, 101), Color.WHITE);
		new SaveButton(new Vector2(75,25), new Vector2(0, ScreenSize.y - 25), Color.GRAY);
		new ExitButton(new Vector2(75,25), new Vector2(77,ScreenSize.y - 25), Color.GRAY);
		new ClearDoodleButton(new Vector2(75,25), new Vector2(154,ScreenSize.y - 25), Color.GRAY);
		_editWindow = new EditWindow(editWindowSize, new Vector2(ScreenSize.x - editWindowSize.x, 0));
		batch = new SpriteBatch();
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		for (Rec2D rec : Rectangles) {
			batch.draw(rec.RecTexture, rec.Position.x, rec.Position.y, rec.Scale.x, rec.Scale.y);
		}
		batch.draw(_editWindow.DoodleTexture, _editWindow.Position.x, _editWindow.Position.y, _editWindow.Scale.x,
				_editWindow.Scale.y);
		for(int i = 0; i < Rectangles.size; i++) {
			 rec = Rectangles.get(i);
			batch.draw(rec.Outline.OutlineTex, rec.Position.x, rec.Position.y, rec.Scale.x, rec.Scale.y);
			}
		
		for(int i = 0; i < Rectangles.size; i++) {
			 rec = Rectangles.get(i);
			if(rec instanceof Button) {
			Button button = (Button) rec;
			if(button.ButtonText == null) continue;
			_font.draw(batch, button.ButtonText, button.Position.x, button.Position.y + button.Scale.y * 0.75f,
			button.Scale.x, Align.center, false);
			}
		}
		
		batch.end();
			
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	public void filesImported(String[] filePaths) {
		Pixmap map = ImageInputOutput.Instance.loadImage(filePaths[0]);
		if (map == null)
			return;
		_editWindow.RecTexture = new Texture(map);
	}

}
