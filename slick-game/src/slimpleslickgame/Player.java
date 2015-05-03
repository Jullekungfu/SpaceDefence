package slimpleslickgame;

import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public abstract class Player {
	
	protected Vector2f position;
	protected Vector2f direction;
	protected Shape shape;
	protected ShapeFill shapeFill;
	private float speed = 5;
	protected byte id;
	protected Gun gun;
	protected HashMap<Integer, Creep> creeps;
	private boolean isMoving;
	
	public void init(){
		shape = new Rectangle(0, 0, 50, 50);
		shapeFill = new GradientFill(0,0, new Color(255, 0, 0), 50, 50, new Color(0, 0, 255), true);
		position = new Vector2f(50,300);
		direction = new Vector2f(0,0);
		isMoving = false;
		gun = new Gun();
		creeps = new HashMap<Integer, Creep>();
	}
	
	public abstract void update(int delta);
	
	protected void updatePosition(){
		if(position != null){
			position.add(direction);
			shape.setLocation(position);
		}
	}
	
	public void render(Graphics graphics){
		for(Creep c : creeps.values()){
			c.render(graphics);
		}
		gun.render(graphics);
		graphics.fill(shape, shapeFill);
	}

	protected void setDirection(Vector2f dir) {
		dir.normalise();
		this.direction = dir.scale(speed);
	}
	
	public void moveTo(Vector2f pos){
		isMoving = true;
		//setDirection();
	}
	
}
