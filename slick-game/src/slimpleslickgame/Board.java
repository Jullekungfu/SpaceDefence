/**
 * 
 */
package slimpleslickgame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * @author nille
 *
 */
public class Board {

	private Path shape;
	
	public Board(Vector2f pos, Vector2f size){
		//shape = new Rectangle(pos.x, pos.y, size.x, size.y);
		shape = new Path(pos.x, pos.y);
		shape.lineTo(pos.x, pos.y + size.y);
		shape.lineTo(pos.x + size.x, pos.y + size.y);
		shape.lineTo(pos.x + size.x, pos.y);
		shape.lineTo(pos.x, pos.y);
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

	public Vector2f getScoreBoardPos() {
		float x = shape.getMinX();
		float y = shape.getMaxY() - 20;
		return new Vector2f(x, y);
	}
	
}
