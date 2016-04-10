package me.bittnerdenaro.lom.entity;

import java.util.List;

import me.bittnerdenaro.lom.BDProjectile;
import me.bittnerdenaro.lom.LeagueOfMinecraft;
import me.bittnerdenaro.lom.LeagueOfMinecraft.Team;
import me.bittnerdenaro.lom.skills.BasicAttack;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public abstract class Minion implements Skillable, Healthable
{
	public LivingEntity me;
	public Team team;
	public LivingEntity target;
	public double health;
	public double maxHealth;
	
	public Minion(LivingEntity entity, Team t, int maxHealth)
	{
		me = entity;
		team = t;
		target = null;
		this.maxHealth = maxHealth;
		health = maxHealth;
		LeagueOfMinecraft.instance.minions.put(entity,this);
		LeagueOfMinecraft.instance.healthables.put(entity,this);
		ai(entity);
	}
	
	public abstract int getRange();
	
	public abstract double getDamage();
	
	public void ai(LivingEntity me)
	{
		me.setAI(false);
		me.setInvulnerable(true);
		
		Minion thisMinion = this;
		
		double range = getRange();
		
		new BukkitRunnable()
		{
			final int repeat = LeagueOfMinecraft.TPS;
			int timer = repeat;
			
			@Override
			public void run()
			{
				if(isDead())
				{
					cancel();
					me.remove();
					return;
				}
				if(target != null)
				{
					if(timer-- == 0)
					{
						timer = repeat;
						if(canStillShoot() && !target.isDead() && !LeagueOfMinecraft.instance.healthables.get(target).isDead())
						{
							Vector dir = target.getLocation().clone().subtract(me.getEyeLocation()).toVector().normalize();
							Location spawnLoc = me.getEyeLocation().clone().add(dir.multiply(2));
							spawnLoc.add(new Vector(0,0.5,0));
							Projectile proj = (Projectile)LeagueOfMinecraft.instance.getWorld().spawnEntity(spawnLoc, EntityType.ARROW);
							new BDProjectile(thisMinion, proj, 0.3, target, new BasicAttack());
						}
						else
						{
							target = null;
						}
					}
				}
				else
				{
					List<Entity> entities = me.getNearbyEntities(range * 2,range * 2,range * 2);
					double distance = range;
					LivingEntity closest = null;
					for(Entity entity : entities)
					{
						if(entity instanceof LivingEntity 
								&& LeagueOfMinecraft.instance.healthables.containsKey(entity)
								&& !LeagueOfMinecraft.instance.sameTeam(me,entity))
						{
							double dist = me.getLocation().distance(entity.getLocation());
							if(dist < distance)
							{
								distance = dist;
								closest = (LivingEntity)entity;
							}
						}
					}
					
					if(closest != null)
					{
						target = closest;
					}
					else
					{
						//Move towards nexus
						Location nexus = LeagueOfMinecraft.instance.map.getOpposingNexus(team);
						Location pos = me.getLocation().clone();
						pos.setY(pos.getY() + 2);
						Vector direction = nexus.clone().subtract(pos).toVector().normalize().multiply(0.1);
						me.setVelocity(direction);
					}
				}
			}
		}.runTaskTimer(LeagueOfMinecraft.instance,1,1);
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
		return health;
	}

	@Override
	public void setHealth(double health)
	{
		this.health = health;
	}
	
	public static void spawnMinion(int type, Team team)
	{
		switch(type)
		{
			case 0:
				spawnMeleeMinion(team);
			return;
			case 1:
				spawnRangedMinion(team);
			return;
			case 2:
				spawnSeigeMinion(team);
			return;
		}
	}
	
	public static void spawnMeleeMinion(Team team)
	{
		Slime slime = (Slime)LeagueOfMinecraft.instance.getWorld().spawnEntity(LeagueOfMinecraft.instance.map.getMyNexus(team),EntityType.SLIME);
		slime.setSize(2);
		new Minion(slime, team, 455)
		{
			@Override
			public int getRange()
			{
				return 2;
			}

			@Override
			public double getDamage()
			{
				return 12;
			}

			
		};
	}
	
	public static void spawnRangedMinion(Team team)
	{
		MagmaCube magmaCube = (MagmaCube)LeagueOfMinecraft.instance.getWorld().spawnEntity(LeagueOfMinecraft.instance.map.getMyNexus(team),EntityType.MAGMA_CUBE);
		magmaCube.setSize(2);
		new Minion(magmaCube, team, 290)
		{

			@Override
			public int getRange()
			{
				return 4;
			}

			@Override
			public double getDamage()
			{
				return 23;
			}
			
		};
	}
	
	public static void spawnSeigeMinion(Team team)
	{
		//Minecart boomcart = (Minecart)LeagueOfMinecraft.instance.getWorld().spawnEntity(map.redNexus,EntityType.MINECART_TNT);
		Zombie zombie = (Zombie)LeagueOfMinecraft.instance.getWorld().spawnEntity(LeagueOfMinecraft.instance.map.getMyNexus(team),EntityType.ZOMBIE);
		zombie.setBaby(true);
		//zombie.setPassenger(boomcart);
		new Minion(zombie, team, 830)
		{
			@Override
			public int getRange()
			{
				return 6;
			}

			@Override
			public double getDamage()
			{
				return 41;
			}
		};
	}

	@Override
	public LivingEntity me()
	{
		return me;
	}
	
	private boolean canStillShoot()
	{
		return me.getLocation().distance(target.getLocation()) < getRange();
	}
}
