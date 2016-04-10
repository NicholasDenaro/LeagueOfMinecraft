package me.bittnerdenaro.lom.skills;

import me.bittnerdenaro.lom.BDSkillShot;
import me.bittnerdenaro.lom.LeagueOfMinecraft;
import me.bittnerdenaro.lom.entity.Healthable;
import me.bittnerdenaro.lom.entity.Skillable;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.util.Vector;

public class TestSkill1 extends Skill {

	public TestSkill1() {
		super("Skill 1", 5);
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
		//event.getPlayer().sendMessage("Used q!");
		Location loc = event.getPlayer().getLocation().clone();
		loc.setPitch(0);
		Vector dir = loc.getDirection().normalize();
		Projectile proj = (Projectile)LeagueOfMinecraft.instance.getWorld().spawnEntity(event.getPlayer().getEyeLocation().clone().add(dir),EntityType.SNOWBALL);
		
		new BDSkillShot(LeagueOfMinecraft.instance.playersChamp.get(event.getPlayer()), proj, 1, 10, dir, this);
	}

	@Override
	public void hit(Skillable player, Healthable entity)
	{
		
	}

}
