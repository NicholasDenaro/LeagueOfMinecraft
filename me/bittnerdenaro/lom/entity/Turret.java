package me.bittnerdenaro.lom.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import me.bittnerdenaro.lom.LeagueOfMinecraft;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowman;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Turret implements Listener
{
	public static Turret instance;
	private HashSet<Entity> turrets = new HashSet<Entity>();
	
	public Turret()
	{
		super();
		instance = this;
	}
	
	public void createTurret(Location location)
	{
		World world = LeagueOfMinecraft.instance.getWorld();
		Snowman turret = (Snowman)world.spawnEntity(location, EntityType.SNOWMAN);
		turret.setAI(false);
		turret.setInvulnerable(true);
		turrets.add(turret);
		
		BukkitRunnable runnable = new BukkitRunnable()
		{
			final int repeat = LeagueOfMinecraft.TICKS;
			int timer = repeat;
			
			@Override
			public void run()
			{
				if(turret.isDead())
				{
					cancel();
				}
				LivingEntity target = turret.getTarget();
				if(target != null)
				{
					if(timer-- == 0)
					{
						timer = repeat;
						//shoot again
						if(canStillShoot(turret,target))
						{
							Vector dir = target.getEyeLocation().clone().subtract(turret.getEyeLocation()).toVector().normalize();
							LeagueOfMinecraft.instance.getWorld().spawnArrow(turret.getEyeLocation().clone().add(dir), dir, 1, 0);
						}
						else
						{
							turret.setTarget(null);;
						}
					}
				}
				else
				{
					//search for enemy
					List<Entity> entities = turret.getNearbyEntities(5,5,5);
					LivingEntity closest = null;
					double distance = 5;
					for(Entity entity : entities)
					{
						if(entity instanceof LivingEntity)
						{
							double dist = turret.getLocation().distance(entity.getLocation());
							if(dist < distance)
							{
								distance = dist;
								closest = (LivingEntity)entity;
							}
						}
					}
					if(closest != null)
					{
						turret.setTarget(closest);
					}
				}
			}
			
		};
		
		runnable.runTaskTimer(LeagueOfMinecraft.instance, 1, 1);
	}
	
	private boolean canStillShoot(Snowman turret, LivingEntity entity)
	{
		return turret.getLocation().distance(entity.getLocation()) < 5;
	}
}
