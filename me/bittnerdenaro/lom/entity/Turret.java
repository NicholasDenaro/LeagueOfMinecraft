package me.bittnerdenaro.lom.entity;

import java.util.HashSet;
import java.util.List;

import me.bittnerdenaro.lom.BDProjectile;
import me.bittnerdenaro.lom.LeagueOfMinecraft;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowman;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Turret implements Listener
{
	public static Turret instance;
	public static final int TURRET_RADIUS = 20;
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
			final int repeat = LeagueOfMinecraft.TPS;
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
							//Arrow arrow = LeagueOfMinecraft.instance.getWorld().spawnArrow(turret.getEyeLocation().clone().add(dir), dir, 0, 0);
							Projectile proj = (Projectile)LeagueOfMinecraft.instance.getWorld().spawnEntity(turret.getEyeLocation().clone().add(dir),EntityType.SNOWBALL);
							//arrow.setBounce(false);
							//arrow.setFallDistance(0);
							new BDProjectile(turret, proj, 0.3, target, new BukkitRunnable(){

								@Override
								public void run()
								{
									//target.sendMessage("You got hit!");
								}
								
							});
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
					List<Entity> entities = turret.getNearbyEntities(TURRET_RADIUS * 2,TURRET_RADIUS * 2,TURRET_RADIUS * 2);
					LivingEntity closest = null;
					double distance = TURRET_RADIUS;
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
		return turret.getLocation().distance(entity.getLocation()) < TURRET_RADIUS;
	}
}
