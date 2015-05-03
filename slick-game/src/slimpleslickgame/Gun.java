/**
 * 
 */
package slimpleslickgame;

import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * @author nille
 *
 */
public class Gun {

	private LinkedList<Bullet> bullets;
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
		bullets = new LinkedList<Bullet>();
	}
	
	public void shoot(Vector2f fromPosition){
		bullets.add(new Bullet(fromPosition));
	}
	
	public void render(Graphics graphics){
		for(Bullet b: bullets){
			b.render(graphics);
		}
	}
	
	public void update(int delta){
		for(Bullet b: bullets){
			b.update(delta);
		}
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
