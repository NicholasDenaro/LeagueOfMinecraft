package me.bittnerdenaro.lom;

import java.util.ArrayList;
import java.util.List;

import me.bittnerdenaro.lom.entity.Skillable;
import me.bittnerdenaro.lom.skills.Skill;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BDSkillShot
{
	private Skillable shooter;
	private Projectile projectile;
	private double speed;
	private double distance;
	private Vector direction;
	private BukkitRunnable onHit;
	private Skill skill;
	
	public BDSkillShot(Skillable shooter, Projectile projectile, double speed, double distance, Vector direction, Skill skill)
	{
		this.shooter = shooter;
		this.projectile = projectile;
		this.speed = speed;
		this.distance = distance;
		this.direction = direction;
		this.skill = skill;
		
		Vector vel = direction.clone().multiply(speed);
		
		projectile.setVelocity(vel);
		
		Location start = projectile.getLocation().clone();
		
		BukkitRunnable runnable = new BukkitRunnable()
		{

			@Override
			public void run()
			{
				if(projectile.isDead() || projectile.getLocation().distance(start) > distance)
				{
					if(projectile.isDead())
					{
					}
					cancel();
					projectile.remove();
					return;
				}
				//LeagueOfMinecraft.instance.getServer().broadcastMessage("move");
				//projectile.setVelocity(new Vector(0,0,0));
				//projectile
				Location current = projectile.getLocation();
				List<Entity> ents = projectile.getNearbyEntities(4,4,4);
				LivingEntity closest = null;
				double distance = 10;
				for(Entity ent : ents)
				{
					if(ent instanceof LivingEntity 
							&& LeagueOfMinecraft.instance.healthables.containsKey(ent) 
							&& ent != shooter)
					{
						double dist = ent.getLocation().distance(current);
						if(dist < distance)
						{
							closest = (LivingEntity)ent;
							distance = dist;
						}
					}
				}
				if(closest != null)
				{
					if(closest.getLocation().distance(current) < 2)
					{
						cancel();
						projectile.remove();
						skill.hit(shooter, LeagueOfMinecraft.instance.healthables.get(closest));
					}
				}
				else
				{
					Vector vel = projectile.getVelocity();
					vel.setY(-vel.getY());
					vel.setY(0);
					projectile.setVelocity(vel);
					
					//Vector dir = target.getEyeLocation().clone().subtract(projectile.getLocation()).toVector().normalize();
					//dir = dir.multiply(speed);
					//projectile.setVelocity(dir);
				}
			}
			
		};
		runnable.runTaskTimer(LeagueOfMinecraft.instance,1,1);
	}
}
