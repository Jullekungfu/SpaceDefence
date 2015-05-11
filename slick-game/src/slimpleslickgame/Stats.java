package slimpleslickgame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import util.Logger;

public class Stats {
	
	private float x, y;
	private int credits = 0;
	private int level = 1;
	private int time = 0;
	
	
	public Stats(Vector2f scoreBoardPos) {
		this.x = scoreBoardPos.x;
		this.y = scoreBoardPos.y;
	}

	public void render(Graphics graphics){
		graphics.drawString("Credits: " + credits, x, y);
	}
	
	public boolean update(int delta, StatEvent statEvent){
		this.time += delta;
		while(time > 100){
			time -= 100;
			credits += level*10;
		}
		this.credits += statEvent.getScoreDiff();
		
		if(this.credits >= level * level * 1000 && statEvent.getTryUpgrade()){
			level++;
			Logger.log("Upgraded player!");
			return true;
			
		}
		return false;
	}

}
