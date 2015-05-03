/**
 * 
 */
package slimpleslickgame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * @author nille
 *
 */
public class Board {

	private Shape shape;
	
	public Board(Vector2f pos, Vector2f size){
		shape = new Rectangle(pos.x, pos.y, size.x, size.y);
	}
	
	public void render(Graphics graphics){
		graphics.draw(shape);
	}

	public Vector2f getPlayerInitPos() {
		float x = shape.getCenterX();
		float y = shape.getMaxY() - 100;
		return new Vector2f(x, y);
	}

	public Shape getShape() {
		return this.shape;
	}
	
}
