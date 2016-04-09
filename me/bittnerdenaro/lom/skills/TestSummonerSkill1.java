package me.bittnerdenaro.lom.skills;

import org.bukkit.event.player.PlayerEvent;

public class TestSummonerSkill1 extends Skill {

	public TestSummonerSkill1() {
		super("Test Sum 1", 1);
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
		event.getPlayer().sendMessage("Sum1");
	}

}
