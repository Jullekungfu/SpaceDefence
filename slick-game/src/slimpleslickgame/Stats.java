package slimpleslickgame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Stats {
	
	private float x, y;
	private int score = 0;
	
	
	public Stats(Vector2f scoreBoardPos) {
		this.x = scoreBoardPos.x;
		this.y = scoreBoardPos.y;
	}

	public void render(Graphics graphics){
		graphics.drawString("Score: " + score, x, y);
	}
	
	public void update(int delta, int score){
		this.score += score;
	}

}
