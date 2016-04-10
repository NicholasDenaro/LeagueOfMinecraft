package me.bittnerdenaro.lom;

import java.util.HashMap;

import net.minecraft.server.v1_9_R1.Tuple;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class CrowdControl implements Listener
{
	private HashMap<Player, Integer> silence = new HashMap<Player, Integer>();
	private HashMap<Player, Tuple<Double, BukkitRunnable>> slow = new HashMap<Player, Tuple<Double, BukkitRunnable>>();
	private HashMap<Player, Integer> stun = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> root = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> polymorph = new HashMap<Player, Integer>();
	private HashMap<Player, Double> knockup = new HashMap<Player, Double>();
	private HashMap<Player, Double> knockback = new HashMap<Player, Double>();
	
	public boolean canCast(Player player)
	{
		return !isStunned(player) && !isSilenced(player) && !isPolymorphed(player) && !isKnockedup(player) && !isKnockedback(player);
	}
	
	public void silence(Player player, int duration)
	{
		silence.put(player,duration);
	}
	
	public void endSilence(Player player)
	{
		silence.remove(player);
	}
	
	public boolean isSilenced(Player player)
	{
		return silence.containsKey(player);
	}
	
	public void slow(Player player, double percent, int duration)
	{
		if(slowAmmount(player) < percent)
		{
			if(slow.containsKey(player))
				slow.remove(player).b().cancel();
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, (int)(percent / 15)));
			BukkitRunnable runnable = new BukkitRunnable()
			{
				@Override
				public void run()
				{
					player.removePotionEffect(PotionEffectType.SLOW);
					endSlow(player);
				}
			};

			slow.put(player, new Tuple<Double, BukkitRunnable>(percent, runnable));
			runnable.runTaskLater(LeagueOfMinecraft.instance,duration);
		}
		else
		{
			
		}
	}
	
	public double slowAmmount(Player player)
	{
		if(slow.containsKey(player))
		{
			return slow.get(player).a();
		}
		
		return 1;
	}
	
	public void endSlow(Player player)
	{
		slow.remove(player);
	}
	
	public void stun(Player player, int duration)
	{
		stun.put(player,duration);
	}
	
	public void endStun(Player player)
	{
		stun.remove(player);
	}
	
	public boolean isStunned(Player player)
	{
		return stun.containsKey(player);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		if(isStunned(player) || isRooted(player))
		{
			event.setCancelled(true);
		}
		if(isKnockedup(player) && !isKnockedback(player))
		{
			Vector vel = event.getPlayer().getVelocity();
			vel.setX(0);
			vel.setZ(0);
			event.getPlayer().setVelocity(vel);
		}
	}
	
	@EventHandler
	public void onPlayerLand(PlayerVelocityEvent event)
	{
		if(event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isSolid() && event.getVelocity().getY() < 0)
		{
			endKnockup(event.getPlayer());
			event.getPlayer().sendMessage("vel: " + event.getVelocity());
		}
	}
	
	public void root(Player player, int duration)
	{
		root.put(player,duration);
	}
	
	public void endRoot(Player player)
	{
		root.remove(player);
	}
	
	public boolean isRooted(Player player)
	{
		return root.containsKey(player);
	}
	
	public void polymorph(Player player, int duration)
	{
		polymorph.put(player,duration);
	}
	
	public void endPolymorph(Player player)
	{
		polymorph.remove(player);
	}
	
	public boolean isPolymorphed(Player player)
	{
		return polymorph.containsKey(player);
	}
	
	public void knockup(Player player, double distance)
	{
		knockup.put(player,distance);
		player.setVelocity(player.getVelocity().setY(distance));
	}
	
	public void endKnockup(Player player)
	{
		knockup.remove(player);
	}
	
	public boolean isKnockedup(Player player)
	{
		return knockup.containsKey(player);
	}
	
	public void knockback(Player player, double distance, Vector direction)
	{
		knockback.put(player, distance);
		player.setVelocity(direction.normalize().multiply(distance));
	}
	
	public void endKnockback(Player player)
	{
		knockback.remove(player);
	}
	
	public boolean isKnockedback(Player player)
	{
		return knockback.containsKey(player);
	}
}
