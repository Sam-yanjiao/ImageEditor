package Utility;

import java.io.IOException;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.imageeditor.ImageEditor;

public class InputManager implements InputProcessor {
	public static InputManager Instance;
	public Array<IClickable> Clickables = new Array<IClickable>();
	public Array<IHoverable> Hoverables = new Array<IHoverable>();
	private IHoverable _currentlyHovered;
	private IClickable _currentlyClicked;

	public InputManager() {
		Instance = this;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector2 worldPosition = new Vector2(screenX, ImageEditor.Instance.ScreenSize.y - screenY);
		IClickable collision = CollisionManager.Instance.getClicked(worldPosition);
		if (collision != null)
			collision.onClickDown(worldPosition);
		_currentlyClicked = collision;
		return true;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (_currentlyClicked != null)
			_currentlyClicked.onClickUp(new Vector2(screenX, ImageEditor.Instance.ScreenSize.y - screenY));
		return false;
	}

	public boolean mouseMoved(int screenX, int screenY) {
		IHoverable collision = CollisionManager.Instance
				.getHovered(new Vector2(screenX, ImageEditor.Instance.ScreenSize.y - screenY));
		if (_currentlyHovered != null && _currentlyHovered != collision)
			_currentlyHovered.onHoverExit();
		if (collision != null)
			collision.onHovered();
		if(collision != _currentlyHovered) _currentlyClicked = null;
		_currentlyHovered = collision;
		return true;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		mouseMoved(screenX, screenY);
		if (_currentlyClicked != null)
			_currentlyClicked.onClickDragged(new Vector2(screenX, ImageEditor.Instance.ScreenSize.y - screenY));
		return false;
	}

	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

	private boolean _controlPressed;

	public boolean keyDown(int keycode) {
		if (_controlPressed && keycode == Keys.S) {
			if (ImageInputOutput.Instance.ImageFolderLocation == null) {
				System.out.println("Image folder location is not specified.");
				return false;
			}
		}
		try {
			ImageInputOutput.Instance
					.saveImage("C:\\Users\\yanjiaoliu\\OneDrive - Southern Utah University\\Desktop\\output.bmp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("YOU PRESSED CONTROL + S!");
		if (keycode == Keys.CONTROL_LEFT)
			_controlPressed = true;
		return false;
	}

	public boolean keyUp(int keycode) {
		if (keycode == Keys.CONTROL_LEFT)
			_controlPressed = false;
		return false;
	}

	public boolean keyTyped(char character) {
		return false;
	}

	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}

}
