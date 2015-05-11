package slimpleslickgame;

public class StatEvent {
	private int scoreDiff;
	private boolean tryUpgrade;
	public StatEvent(){
		this.scoreDiff = 0;
		this.tryUpgrade = false;
	}
	
	public int getScoreDiff(){
		return scoreDiff;
	}
	
	public boolean getTryUpgrade(){
		return tryUpgrade;
	}

	public void setCreditsDiff(int score) {
		this.scoreDiff = score;
	}
	
	public void setTryUpgrade(){
		this.tryUpgrade = true;
	}

}
