package me.bittnerdenaro.lom;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class GeneralPlayerEvents implements Listener
{
	public int movespeed = 1;
	
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
				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						double difference = alteredBlockLocation.distance( player.getLocation() );
						if( difference > 1 )
						{
							Location relativeLocation = blockLocation.clone().subtract( playerLocation );
							event.getPlayer().setVelocity( new Vector(relativeLocation.getX()*movespeed, 0, relativeLocation.getZ()*movespeed).normalize() );	
						
						}
						else
						{
							this.cancel();
						}
					}
				}.runTaskTimer(LeagueOfMinecraft.instance, 0, 1);
				
			}
		}
	}
}
