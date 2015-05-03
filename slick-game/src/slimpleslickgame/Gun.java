/**
 * 
 */
package slimpleslickgame;

import java.util.HashMap;
import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import util.EventProtocol;
import client.MessageWrapper;

/**
 * @author nille
 *
 */
public class Gun {

	private HashMap<Integer, Bullet> bullets;
	private int bulletID;
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
		bullets = new HashMap<Integer, Bullet>();
		bulletID = 0;
	}
	
	public void shoot(Vector2f fromPosition){
		bullets.put(bulletID, new Bullet(fromPosition));
		bulletID++;
	}
	
	public void render(Graphics graphics){
		for(Bullet b: bullets.values()){
			b.render(graphics);
		}
	}
	
	public void update(int delta){
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
	
	/**
	 * When upgrading weapon.
	 */
	public void upgrade(){
		this.level++;
		this.damage = this.level * 10;
		this.firerate = this.level;
	}
}
