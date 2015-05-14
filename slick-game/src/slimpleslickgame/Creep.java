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
	private int value = 10;
	
	protected final float WIDTH = 20;
	protected final float HEIGHT = 20;
	
	public Creep(Vector2f initPos, Color color){
		hp = 100;
		shape = new Rectangle(0, 0, WIDTH, HEIGHT);
		shapeFill = new GradientFill(0,0, color, 20, 20, color, true);
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
	
	public void render(Graphics graphics){
		if(hp > 0 && !isDestroyed){
//			graphics.fill(shape, shapeFill);
			graphics.draw(shape, shapeFill);
		}
	}
	
	public Vector2f getPosition(){
		return position;
	}

	public Shape getShape() {
		return shape;
	}
	
	public int getScoreValue(){
		return value;
	}
}
