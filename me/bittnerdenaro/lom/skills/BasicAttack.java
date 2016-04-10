package me.bittnerdenaro.lom.skills;

import me.bittnerdenaro.lom.entity.Skillable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

public class BasicAttack extends Skill
{

	public BasicAttack()
	{
		super("basic",0);
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
	}

	@Override
	public void hit(Skillable player, LivingEntity entity)
	{
		// TODO Auto-generated method stub
		
	}
	
}
