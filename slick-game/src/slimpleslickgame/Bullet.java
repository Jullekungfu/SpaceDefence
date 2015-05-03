package slimpleslickgame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

public class Bullet {

	private Circle shape;
	private ShapeFill fill;
	private Vector2f position;
	private Vector2f dir;
	
	public Bullet(Vector2f initPos){
		this.position = initPos.copy();
		this.dir = new Vector2f(0, -2);
		this.shape = new Circle(this.position.x, this.position.y, 5);
		this.fill = new GradientFill(-10, -10, Color.white, 10, 10, Color.white, true);
	}
	
	public void update(int delta){
		this.position.add(dir);
		shape.setCenterX(position.x);
		shape.setCenterY(position.y);
	}
	
	public void render(Graphics graphics){
		graphics.draw(shape, fill);
	}
}