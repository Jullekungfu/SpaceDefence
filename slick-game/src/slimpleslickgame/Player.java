package slimpleslickgame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Player {
	
	private Vector2f position;
	private Vector2f direction;
	private Shape shape;
	private ShapeFill shapeFill;
	
	public void init(){
		shape = new Rectangle(0, 0, 50, 50);
		shapeFill = new GradientFill(0,0, new Color(255, 0, 0), 50, 50, new Color(0, 0, 255), true);
		position = new Vector2f(50,300);
		direction = new Vector2f(0,0);
	}
	
	public void changeDirection(float x, float y){
		direction.set(x, y);
	}
	
	public void update(){
		position.add(direction);
		shape.setLocation(position);
	}
	
	public void render(Graphics graphics){
		graphics.fill(shape, shapeFill);
	}
	
}