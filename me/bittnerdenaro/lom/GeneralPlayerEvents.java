package me.bittnerdenaro.lom;

import me.bittnerdenaro.lom.champions.Champion;
import me.bittnerdenaro.lom.champions.TestChampion;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class GeneralPlayerEvents implements Listener
{
	/*rip
	public double movespeed = .5;
	private HashMap<Player, BukkitRunnable> movementHashMap = new HashMap<Player, BukkitRunnable>();
	
	//let player move to block by right clicking it
	@EventHandler
	public void rightClickBlockMove( PlayerInteractEvent event )
	{
		//make sure right click on block
		if( event.getAction() == Action.RIGHT_CLICK_BLOCK )
		{
			Player player = event.getPlayer();
			Block block = event.getClickedBlock();
			Location blockLocation = event.getClickedBlock().getLocation();
			Location playerLocation = event.getPlayer().getLocation();
			Location alteredBlockLocation = blockLocation.clone();
			alteredBlockLocation.setY(  playerLocation.getY() );
			
			
			//make sure block is ground (one below player)
			if( playerLocation.getY() - blockLocation.getY() == 1 )
			{
				//check if previous move command issued, if so cancel
				BukkitRunnable movementBR = movementHashMap.remove( player );
				if( movementBR != null )
					movementBR.cancel();
				
				BukkitRunnable newBR = new BukkitRunnable()
				{
					@Override
					public void run()
					{
						double difference = alteredBlockLocation.distance( player.getLocation() );
						if( difference > 1 )
						{
							Location relativeLocation = blockLocation.clone().subtract( playerLocation );
							event.getPlayer().setVelocity( new Vector(relativeLocation.getX(), 0, relativeLocation.getZ()).normalize().multiply(movespeed) );	
							
						}
						else
						{
							movementHashMap.remove(player);
							this.cancel();
						}
					}
				};
				
				movementHashMap.put(player, newBR );

				newBR.runTaskTimer(LeagueOfMinecraft.instance, 1, 1);
				
			}
		}
	}*/
	
	@EventHandler
	public void onPlayerJoin( PlayerJoinEvent event )
	{
		Champion ts = new TestChampion(event.getPlayer());
	}
}
