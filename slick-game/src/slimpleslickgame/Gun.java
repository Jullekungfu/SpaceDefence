/**
 * 
 */
package slimpleslickgame;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * @author nille
 *
 */
public class Gun {

	private ArrayList<Bullet> bullets;
	private Vector2f position;
	private Shape shape;
	private Vector2f direction;
	private int level;
	private float speed = 10;
	private int firerate;
	private int damage;
	
	public Gun(){
		this.firerate = 1;
		this.damage = 10;
		this.level = 1;
		bullets = new ArrayList<Bullet>();
	}
	
	public void shoot(Vector2f fromPosition){
	}
	
	protected void updatePosition(){
		if(position != null){
			position.add(direction);
			shape.setLocation(position);
		}
	}
	
	
	protected void setDirection(Vector2f dir) {
		dir.normalise();
		this.direction = dir.scale(speed);
	}
	
	
	/**
	 * When upgrading weapon.
	 */
	public void upgrade(){
		this.level++;
		this.damage = this.level * 10;
		this.firerate = this.level;
	}
}
