/**
 * 
 */
package slimpleslickgame;

import java.util.List;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * @author nille
 *
 */
public class GameInstance {
	
	private Player player;
	private List<Creep> creeps;
	private Shape board;
	
	public GameInstance(Player player, Shape board){
		this.player = player;
		this.board = board;
		
		this.player.position = new Vector2f(board.getCenterX(), board.getMaxY()-50);
	}
	
	public void startCreeps(){
		
	}
	
	
	

}
