package com.mygdx.imageeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class InputManager implements InputProcessor {
    public Array<Button> Buttons = new Array<Button>();
	public static InputManager Instance;

    public InputManager() {
    	
    	Instance = this;
    }
    public boolean keyDown(int keycode) {return false;}

    public boolean keyUp(int keycode) {return false;}

    public boolean keyTyped(char character) {return false;}

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    	Button collision = CollisionManager.Instance.getCollision(
    			new Vector2(screenX, ImageEditor.Instance.ScreenSize.y - screenY));
    	if(collision == null) return false;
    	collision.onPressed();
    	return true;
    	}

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}

    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {return false;}

    public boolean touchDragged(int screenX, int screenY, int pointer) {return false;}

    public boolean mouseMoved(int screenX, int screenY) {return false;}
        
    

    public boolean scrolled(float amountX, float amountY) {return false;}
        
    

   
}
