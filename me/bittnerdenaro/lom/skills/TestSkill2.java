package me.bittnerdenaro.lom.skills;

import me.bittnerdenaro.lom.entity.Healthable;
import me.bittnerdenaro.lom.entity.Skillable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class TestSkill2 extends Skill {

	public TestSkill2() {
		super("Skill 2", 5);
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
		event.getPlayer().sendMessage("Used w!");
	}

	@Override
	public void hit(Skillable player, Healthable entity)
	{
	}

}
