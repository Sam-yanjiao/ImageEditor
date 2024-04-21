package com.mygdx.imageeditor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Button extends Rec2D{
	private static int _buttonCount;
	private int _buttonNumber;
	private Color _startColor;
	public Button(Vector2 scale, Vector2 position, Color recColor) {
		super(position, scale, recColor);
		_buttonCount +=1;
		_buttonNumber = _buttonCount;
		_startColor = recColor;
		InputManager.Instance.Buttons.add(this);
	}
	
	public void onHovered() {
		_recColor = new Color(_startColor.r / 2f, _startColor.g / 2f, _startColor.b / 2f,1);
		generateTexture();
		 System.out.println("You've hovering over" + _buttonNumber);
	}
	public void onHoverExit() {
		_recColor = _startColor;
		generateTexture();
	}
	
    public Button onPressed() {
        System.out.println("You've pressed button" + _buttonNumber);
        return null;
    }
}
   
