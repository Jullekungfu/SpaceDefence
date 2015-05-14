package slimpleslickgame;

import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Polygon;
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
	protected Stats stats;
	protected boolean dead;
	
	private final float WIDTH = 20;
	private final float HEIGHT = 20;
	
	public Player(byte id){
		this.id = id;
		dead = false;
	}
	
	public void init(Vector2f centerPos, Stats stats){
		this.stats = stats;
		position = new Vector2f(centerPos.x - WIDTH/2, centerPos.y - HEIGHT/2);
		shape = new Polygon(new float[]{position.x, position.y, position.x + WIDTH/2, position.y - HEIGHT, position.x + WIDTH, position.y});
		shape.setLocation(position);
		Color color;
		switch(id){
			case 0x1: 
				color = Color.red;
				break;
			case 0x2:
				color= Color.green;
				break;
			case 0x3:
				color = Color.cyan;
				break;
			default:
				color = Color.yellow;
				break;
		}
		shapeFill = new GradientFill(0,0, color, 50, 50, color, true);
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
				if(shape.getX() <= containerShape.getMinX()){
					position.set(containerShape.getMinX(), position.y);
				}else if(shape.getMaxX() >= containerShape.getMaxX()){
					position.set(containerShape.getMaxX()-WIDTH, position.y);
				}
				
				shape.setLocation(position);
			}
		}
	}
	
	public void render(Graphics graphics, Shape containerShape){
		for(Creep c : creeps.values()){
			c.render(graphics);
		}
		gun.render(graphics);
		graphics.draw(shape, shapeFill);
		this.stats.render(graphics);
		
		if(dead){
			graphics.drawString("DEAD", containerShape.getCenterX()-10, containerShape.getCenterY());
		}
	}

	protected void setDirection(Vector2f dir) {
		dir.normalise();
		this.direction = dir.scale(speed);
	}
	
}
