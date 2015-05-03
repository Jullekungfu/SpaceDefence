/**
 * 
 */
package slimpleslickgame;

import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * @author nille
 *
 */
public class GameInstance {
	
	private Player player;
	private List<Creep> creeps;
	private Board board;
	
	public GameInstance(Player player, Vector2f pos, Vector2f size){
		this.player = player;
		this.board = new Board(pos, size);
		this.player.moveTo(board.getPlayerInitPos());
	}
	
	public void startCreeps(){
		
	}
	
	public void update(int delta){
		player.update(delta);
		
	}
	
	public void render(Graphics graphics){
		board.render(graphics);
	}
	
	

}
