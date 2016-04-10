package me.bittnerdenaro.lom.skills;

import org.bukkit.event.player.PlayerEvent;

public abstract class Skill 
{
	public String name;
	public int level;
	public int maxLevel;
	
	public Skill( String name, int maxLevel )
	{
		this.name = name;
		this.maxLevel = maxLevel;
		level = 0;
	}
	
	//returns true if skill is below cap
	public boolean levelUp()
	{
		if( this.level < this.maxLevel )
		{
			this.level++;
			return true;
		}
		else
			return false;
	}
	
	public boolean levelCapped()
	{
		return this.level == this.maxLevel;
	}
	
	public int getLevel()
	{
		return this.level;
	}
	
	public abstract int getDamage();
	
	public abstract int getCooldown();
	
	public abstract int getManaCost();
	
	public abstract void use( PlayerEvent event );
}
