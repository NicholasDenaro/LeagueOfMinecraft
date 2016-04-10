package me.bittnerdenaro.lom.entity;

import java.util.List;

import me.bittnerdenaro.lom.BDProjectile;
import me.bittnerdenaro.lom.LeagueOfMinecraft;
import me.bittnerdenaro.lom.LeagueOfMinecraft.Team;
import me.bittnerdenaro.lom.skills.TestSkill1;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowman;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Turret implements Skillable, Healthable
{
	public static Turret instance;
	public static final int TURRET_RADIUS = 20;
	public double health;
	public double maxHealth;
	
	private LivingEntity me;
	public Team team;
	
	public Turret(LivingEntity entity, Team team, int maxHealth)
	{
		super();
		this.me = entity;
		this.team = team;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		LeagueOfMinecraft.instance.turrets.put(entity, this);
		LeagueOfMinecraft.instance.healthables.put(entity, this);
	}
	
	public void createTurret(Location location, Team team)
	{
		World world = LeagueOfMinecraft.instance.getWorld();
		Snowman turret = (Snowman)world.spawnEntity(location, EntityType.SNOWMAN);
		turret.setAI(false);
		turret.setInvulnerable(true);
		
		Turret thisTurret = new Turret(turret, team, 1500);
		
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
					return;
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
							Projectile proj = (Projectile)LeagueOfMinecraft.instance.getWorld().spawnEntity(turret.getEyeLocation().clone().add(dir.multiply(2)),EntityType.SHULKER_BULLET);
							new BDProjectile(thisTurret, proj, 0.3, target, new TestSkill1());
						}
						else
						{
							turret.setTarget(null);
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
						if(entity instanceof LivingEntity && !LeagueOfMinecraft.instance.sameTeam(entity, turret))
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

	@Override
	public void damage(double amount)
	{
		health -= amount;
	}
	
	@Override
	public boolean isDead()
	{
		return health <= 0;
	}

	@Override
	public double getHealth()
	{
		// TODO Auto-generated method stub
		return health;
	}

	@Override
	public void setHealth(double health)
	{
		this.health = health;
	}
}
