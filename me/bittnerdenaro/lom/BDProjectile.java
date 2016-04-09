package me.bittnerdenaro.lom;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BDProjectile
{
	private Entity shooter;
	private Projectile projectile;
	private double speed;
	private LivingEntity target;
	private BukkitRunnable onHit;
	
	public BDProjectile(Entity shooter, Projectile projectile, double speed, LivingEntity target, BukkitRunnable onHit)
	{
		this.shooter = shooter;
		this.projectile = projectile;
		this.speed = speed;
		this.target = target;
		this.onHit = onHit;
		
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
					onHit.run();
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
	
	
}