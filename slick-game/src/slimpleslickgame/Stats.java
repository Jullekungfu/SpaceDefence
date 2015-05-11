package slimpleslickgame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import util.Logger;

public class Stats {
	
	private float x, y;
	private int credits = 0;
	private int level = 1;
	private int time = 0;
	private int scoreDiff = 0;
	private boolean tryUpgrade = false;
	
	
	public Stats(Vector2f scoreBoardPos) {
		this.x = scoreBoardPos.x;
		this.y = scoreBoardPos.y;
	}

	public void render(Graphics graphics){
		graphics.drawString("Credits: " + credits, x, y);
	}
	
	public boolean update(int delta){
		this.time += delta;
		while(time > 1000){
			time -= 1000;
			credits += level;
		}
		this.credits += scoreDiff;
		this.scoreDiff = 0;
		
		if(this.credits >= level * level * 1000 && this.tryUpgrade){
			level++;
			this.tryUpgrade = false;
			Logger.log("Upgraded player!");
			return true;
			
		}
		return false;
	}

	public void putUpgradePressed() {
		this.tryUpgrade = true;
	}

	public void addCreditsDiff(int score) {
		this.scoreDiff += score;
	}

}
