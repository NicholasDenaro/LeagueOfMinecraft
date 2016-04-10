package me.bittnerdenaro.lom.champions;

import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class Stat  
{
	public double value;
	public Score score;
	public int order; //>0 for sidebar, <0 for under name
	
	public Stat(  double value, Score score, int order )
	{
		this.value = value;
		this.score = score;
		this.order = order;
	}
	
	
	public String getScoreStringBase()
	{
		return this.score.getEntry().substring(0, this.score.getEntry().indexOf(":")+1) + " ";
	}
	
	public void update( Scoreboard board, Objective objective )
	{
		Score newScore = objective.getScore( this.getScoreStringBase() + this.value );
		board.resetScores(this.score.getEntry());
		this.score = newScore;
		newScore.setScore(this.order);
	}
}
