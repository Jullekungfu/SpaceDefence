package slimpleslickgame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Creep {
	
	private int hp;
	private Vector2f position;
	private Vector2f direction;
	private Shape shape;
	private ShapeFill shapeFill;
	private float speed = 50;
	private boolean isDestroyed;
	
	public Creep(Vector2f initPos){
		hp = 100;
		shape = new Rectangle(0, 0, 20, 20);
		shapeFill = new GradientFill(0,0, new Color(0, 255, 0), 20, 20, new Color(0, 0, 255), true);
		position = initPos;
		direction = new Vector2f(0, 0.01f*speed);
		shape.setLocation(position);
		isDestroyed = false;
	}
	
	public void onHit(){
		hp -= 10;
	}
	
	public void update(int delta){
		position.add(direction);
		shape.setLocation(position);
	}
	
	public void destroy(){
		this.isDestroyed = true;
		this.direction = new Vector2f(0, 0);
	}
	
	public boolean isAlive(){
		return !isDestroyed;
	}
	
	public boolean intersects(Bullet b){
		return shape.intersects(b.getShape());
	}
	
	public void render(Graphics graphics){
		if(hp > 0 && !isDestroyed){
			graphics.fill(shape, shapeFill);
		}
	}
	

}
