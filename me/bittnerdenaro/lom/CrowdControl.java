package me.bittnerdenaro.lom;

import java.util.HashMap;

import net.minecraft.server.v1_9_R1.Tuple;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class CrowdControl
{
	private HashMap<Player, Integer> silence = new HashMap<Player, Integer>();
	private HashMap<Player, Tuple<Double, BukkitRunnable>> slow = new HashMap<Player, Tuple<Double, BukkitRunnable>>();
	private HashMap<Player, Integer> stun = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> root = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> polymorph = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> knockup = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> knockback = new HashMap<Player, Integer>();
	
	
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
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, (int)(percent / 10)));
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
	public void onPlayerStunnedOrRoot(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		if(isStunned(player) || isRooted(player))
		{
			event.setCancelled(true);
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
	
	public void knockup(Player player, int duration)
	{
		knockup.put(player,duration);
		player.setVelocity(player.getVelocity().setY(3));
	}
	
	public void endKnockup(Player player)
	{
		knockup.remove(player);
	}
	
	public boolean isKnockedup(Player player)
	{
		return knockup.containsKey(player);
	}
	
	public void knockback(Player player, int duration)
	{
		knockback.put(player,duration);
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
