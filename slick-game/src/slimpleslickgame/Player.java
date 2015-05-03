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
	protected boolean started;
	private Vector2f moveTo;
	protected HashMap<Integer, Creep> creeps;
	
	private final float WIDTH = 50;
	private final float HEIGHT = 50;
	
	public void init(Vector2f centerPos){
		position = new Vector2f(centerPos.x - WIDTH/2, centerPos.y - HEIGHT/2);
		shape = new Rectangle(position.x, position.y, WIDTH, HEIGHT);
		shapeFill = new GradientFill(0,0, new Color(255, 0, 0), 50, 50, new Color(0, 0, 255), true);
		direction = new Vector2f(0,0);
		gun = new Gun();
		moveTo = null;
		creeps = new HashMap<Integer, Creep>();
	}
	
	public abstract void update(int delta, Shape containerShape);
	
	protected void updatePosition(Shape containerShape){
		if(position != null){
			position.add(direction);
			shape.setLocation(position);
			if(shape.intersects(containerShape)){
				System.out.println("intersecting: " + position.toString());
				if(shape.getX() <= containerShape.getX()){
					position.set(containerShape.getX(), position.y);
				}else if(shape.getMaxX() >= containerShape.getMaxX()){
					position.set(containerShape.getMaxX()-WIDTH, position.y);
				}
				
				shape.setLocation(position);
			}
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
		moveTo = pos;
		setDirection(pos.sub(this.position));
	}
	
}
