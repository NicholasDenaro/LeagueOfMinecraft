package me.bittnerdenaro.lom;

import me.bittnerdenaro.lom.LeagueOfMinecraft.Team;
import me.bittnerdenaro.lom.entity.Turret;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEvents implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if("create-turret".equals(command.getName()))
			{
				if(args.length != 1)
				{
					return false;
				}
				if("red".equals(args[0]))
				{
					Turret.instance.createTurret(player.getLocation(), Team.RED);
				}
				if("blue".equals(args[0]))
				{
					Turret.instance.createTurret(player.getLocation(), Team.BLUE);
				}
			}
			else if("slow".equals(command.getName()))
			{
				if(args.length != 2)
				{
					return false;
				}
				double percent = new Double(args[0]);
				int duration = new Integer(args[1]);
				LeagueOfMinecraft.instance.cc.slow(player,percent,duration);
			}
			else if("knockup".equals(command.getName()))
			{
				if(args.length != 1)
				{
					return false;
				}
				double vel = new Double(args[0]);
				LeagueOfMinecraft.instance.cc.knockup(player,vel);
			}
			else if("knockback".equals(command.getName()))
			{
				if(args.length != 1)
				{
					return false;
				}
				
				Location loc = player.getLocation().clone();
				loc.setPitch(10);
				double vel = new Double(args[0]);
				LeagueOfMinecraft.instance.cc.knockback(player, vel, loc.getDirection().multiply(-1));
			}
		}
		return true;
	}
	
}
