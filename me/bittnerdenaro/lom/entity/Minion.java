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

public abstract class Minion extends Skillable
{
	public LivingEntity me;
	public Team team;
	public LivingEntity target;
	
	public Minion(LivingEntity entity, Team t)
	{
		me = entity;
		team = t;
		target = null;
		LeagueOfMinecraft.instance.minions.put(entity,this);
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
				if(target != null)
				{
					if(timer-- == 0)
					{
						timer = repeat;
						if(canStillShoot())
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
					double distance = range + 1;
					LivingEntity closest = null;
					for(Entity entity : entities)
					{
						if(entity instanceof LivingEntity && !LeagueOfMinecraft.instance.sameTeam(me,entity))
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
	
	public static void spawnMinion(int type)
	{
		switch(type)
		{
			case 0:
				spawnMeleeMinion();
			return;
			case 1:
				spawnRangedMinion();
			return;
			case 2:
				spawnSeigeMinion();
			return;
		}
	}
	
	public static void spawnMeleeMinion()
	{
		Slime slime = (Slime)LeagueOfMinecraft.instance.getWorld().spawnEntity(LeagueOfMinecraft.instance.map.redNexus,EntityType.SLIME);
		slime.setSize(2);
		new Minion(slime,Team.RED)
		{

			@Override
			public int getRange()
			{
				return 5;
			}

			@Override
			public double getDamage()
			{
				return 10;
			}
			
		};
	}
	
	public static void spawnRangedMinion()
	{
		MagmaCube magmaCube = (MagmaCube)LeagueOfMinecraft.instance.getWorld().spawnEntity(LeagueOfMinecraft.instance.map.redNexus,EntityType.MAGMA_CUBE);
		magmaCube.setSize(2);
		new Minion(magmaCube,Team.RED)
		{

			@Override
			public int getRange()
			{
				return 7;
			}

			@Override
			public double getDamage()
			{
				return 10;
			}
			
		};
	}
	
	public static void spawnSeigeMinion()
	{
		//Minecart boomcart = (Minecart)LeagueOfMinecraft.instance.getWorld().spawnEntity(map.redNexus,EntityType.MINECART_TNT);
		Zombie zombie = (Zombie)LeagueOfMinecraft.instance.getWorld().spawnEntity(LeagueOfMinecraft.instance.map.redNexus,EntityType.ZOMBIE);
		zombie.setBaby(true);
		//zombie.setPassenger(boomcart);
		new Minion(zombie,Team.RED)
		{

			@Override
			public int getRange()
			{
				return 7;
			}

			@Override
			public double getDamage()
			{
				return 20;
			}
			
		};
	}
	
	private boolean canStillShoot()
	{
		return me.getLocation().distance(target.getLocation()) < getRange();
	}
}
