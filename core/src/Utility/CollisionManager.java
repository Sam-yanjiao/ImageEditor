package Utility;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.imageeditor.Rec2D;

public class CollisionManager {
    public static CollisionManager Instance;
    public CollisionManager() {
        Instance = this;
    }
    
    public IClickable getClicked(Vector2 coordinates) {
    	Rec2D clicked;
    	for(int i = 0; i<InputManager.Instance.Clickables.size; i++) {
    		clicked = (Rec2D) InputManager.Instance.Clickables.get(i);
			if(coordinates.x > clicked.Position.x && coordinates.x < clicked.Position.x + clicked.Scale.x) {
    			if(coordinates.y > clicked.Position.y && coordinates.y < clicked.Position.y + clicked.Scale.y)
    				return (IClickable) clicked;
    		}
    	}
    	return null;
    }

    public IHoverable getHovered(Vector2 coordinates) {
    	Rec2D hovered;
        for(int i = 0; i<InputManager.Instance.Hoverables.size; i++) {
        	hovered = (Rec2D) InputManager.Instance.Hoverables.get(i);
        	if(coordinates.x >= hovered.Position.x && coordinates.x < hovered.Position.x + hovered.Scale.x) {
        		if(coordinates.y >= hovered.Position.y && coordinates.y < hovered.Position.y + hovered.Scale.y)
        			return (IHoverable) hovered;
        	}
        }

        return null;
    }
    
}
