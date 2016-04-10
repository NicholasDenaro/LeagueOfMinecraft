package me.bittnerdenaro.lom.skills;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerEvent;

public class BasicAttack extends Skill
{

	public BasicAttack(String name, int maxLevel)
	{
		super(name,maxLevel);
	}

	@Override
	public int getDamage()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCooldown()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getManaCost()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void use(PlayerEvent event)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hit(LivingEntity entity)
	{
		// TODO Auto-generated method stub
		
	}
	
}
