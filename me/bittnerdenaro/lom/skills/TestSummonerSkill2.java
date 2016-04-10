package me.bittnerdenaro.lom.skills;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class TestSummonerSkill2 extends Skill {

	public TestSummonerSkill2() {
		super("Test Summoner 2", 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCooldown() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getManaCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void use(PlayerEvent event) {
		// TODO Auto-generated method stub
			event.getPlayer().sendMessage("Test sum 2");
	}

	@Override
	public void hit(LivingEntity entity)
	{
	}

}
