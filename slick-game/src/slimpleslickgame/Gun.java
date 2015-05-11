/**
 * 
 */
package slimpleslickgame;

import java.util.HashMap;
import java.util.Map.Entry;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * @author nille
 *
 */
public class Gun {

	private HashMap<Integer, Bullet> bullets;
	private int bulletID;
	private int level;
	private float speed = 10;
	private int firerate;
	private int damage;
	private int coolDown = 0;
	
	public Gun(){
		this.firerate = 2;
		this.damage = 10;
		this.level = 1;
		bullets = new HashMap<Integer, Bullet>();
		bulletID = 0;
	}
	
	public boolean shoot(Vector2f fromPosition){
		if(coolDown <= 0){
			bullets.put(bulletID, new Bullet(fromPosition));
			bulletID++;
			coolDown = 1000/firerate;
			return true;
		}
		return false;
	}
	
	public void render(Graphics graphics){
		for(Bullet b: bullets.values()){
			b.render(graphics);
		}
	}
	
	public void update(int delta){
		if(coolDown > 0){
			coolDown -= delta;
		}
		for(Bullet b: bullets.values()){
			b.update(delta);
		}
	}
	
	public int getbulletID(){
		return bulletID;
	}
	
	public void delete(int id){
		bullets.remove(id);
	}
	
	public int bulletIntersectsCreep(Shape creep){
		for(Entry<Integer, Bullet> bullet : bullets.entrySet()){
			if(creep.intersects(bullet.getValue().getShape())){
				this.delete(bullet.getKey());
				return bullet.getKey();
			}
		}
		return -1;
	}
	
	/**
	 * When upgrading weapon.
	 */
	public void upgrade(){
		this.damage = this.level * 10;
		this.firerate++;
	}
}
