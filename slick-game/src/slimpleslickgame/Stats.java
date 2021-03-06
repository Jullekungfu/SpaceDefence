package slimpleslickgame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import util.Logger;

public class Stats {
	
	private float x, y;
	private int credits = 0;
	private int level = 1;
	private int time = 0;
	private boolean tryUpgrade = false;
	private int hp = 100;
	private int incomeRate = 1000;
	
	public Stats(Vector2f scoreBoardPos) {
		this.x = scoreBoardPos.x;
		this.y = scoreBoardPos.y;
	}

	public void render(Graphics graphics){
		graphics.drawString("Credits: " + credits , x, y-(2*graphics.getFont().getLineHeight()));
		graphics.drawString("Level: " + level , x, y-graphics.getFont().getLineHeight());
		graphics.drawString("HP: "+ hp , x, y);
	}
	
	public boolean update(int delta, int score){
		credits = score;
		return this.update(delta);
	}
	
	public boolean update(int delta){
		int levelCredits = 100 * this.level * this.level;
		if(this.credits >= levelCredits && this.tryUpgrade){
			level++;
			this.credits -= levelCredits;
			Logger.log("Upgraded player!");
			return true;
		}
		this.tryUpgrade = false;
		return false;
	}

	public void putUpgradePressed() {
		this.tryUpgrade = true;
	}

	public int calcCredits(int delta, int scoreDiff) {
		int creditsDiff = scoreDiff;
		this.time += delta;
		while(time > incomeRate){
			time -= incomeRate;
			creditsDiff += level;
		}
		this.credits += creditsDiff;
		return this.credits;
	}
	
	//public in orderto write in game info screen 
	public static final int CREEP_PRICE_1 = 50;
	public static final int CREEP_PRICE_5 = 225;
	public static final int CREEP_PRICE_10 = 400;
	
	private static final int INCOME_RATE_1 = 5;
	private static final int INCOME_RATE_5 = 30;
	private static final int INCOME_RATE_10 = 75;
	
	public int buyCreeps(int key) {
		switch(key){
			case 1:
				if(this.credits >= CREEP_PRICE_1){
					credits -= CREEP_PRICE_1;
					this.incomeRate -= INCOME_RATE_1;
					return 1;
				}
				break;
			case 2:
				if(this.credits >= CREEP_PRICE_5){
					credits -= CREEP_PRICE_5;
					this.incomeRate -= INCOME_RATE_5;
					return 5;
				}
				break;
			case 3:
				if(this.credits >= CREEP_PRICE_10){
					credits -= CREEP_PRICE_10;
					this.incomeRate -= INCOME_RATE_10;
					return 10;
				}
				break;
		}
		return 0;
	}
	
	public int damaged() {
		hp--;
		return hp;
	}

	public void setHP(int playerHp) {
		this.hp = playerHp;
	}

}
