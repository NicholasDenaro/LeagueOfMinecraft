package me.bittnerdenaro.lom;

import me.bittnerdenaro.lom.entity.Skillable;
import me.bittnerdenaro.lom.skills.Skill;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BDProjectile implements Listener
{
	private Skillable shooter;
	private Projectile projectile;
	private double speed;
	private LivingEntity target;
	private Skill skill;
	
	public BDProjectile(Skillable shooter, Projectile projectile, double speed, LivingEntity target, Skill skill)
	{
		this.shooter = shooter;
		this.projectile = projectile;
		this.speed = speed;
		this.target = target;
		this.skill = skill;
		
		LeagueOfMinecraft.instance.getServer().getPluginManager().registerEvents(this, LeagueOfMinecraft.instance);
		
		Vector dir = target.getEyeLocation().clone().subtract(projectile.getLocation()).toVector().normalize();
		dir = dir.multiply(speed);
		projectile.setVelocity(dir);
		
		BukkitRunnable runnable = new BukkitRunnable()
		{

			@Override
			public void run()
			{
				//projectile.setVelocity(new Vector(0,0,0));
				//projectile
				Location current = projectile.getLocation();
				Location fin = target.getLocation();
				if(fin.distance(current) < 2)
				{
					cancel();
					projectile.remove();
					skill.hit(shooter, LeagueOfMinecraft.instance.healthables.get(target));
				}
				else
				{
					//Vector vel = projectile.getVelocity();
					//vel.setY(-vel.getY());
					//vel.setY(0);
					//projectile.setVelocity(vel);
					
					Vector dir = target.getEyeLocation().clone().subtract(projectile.getLocation()).toVector().normalize();
					dir = dir.multiply(speed);
					projectile.setVelocity(dir);
				}
			}
			
		};
		runnable.runTaskTimer(LeagueOfMinecraft.instance,1,1);
	}
	
	@EventHandler
	public void onProjectileHit(EntityDamageByEntityEvent event)
	{
		if(event.getDamager() == this.projectile)
		{
			if(LeagueOfMinecraft.instance.sameTeam(event.getEntity(), shooter.me()))
			{
				event.setCancelled(true);
				if(projectile.isDead())
				{
					LeagueOfMinecraft.instance.getServer().broadcastMessage("whoops, it died too soon.");
				}
				projectile.getLocation().add(projectile.getVelocity());
			}
		}
	}
}
