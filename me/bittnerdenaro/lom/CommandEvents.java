package me.bittnerdenaro.lom;

import me.bittnerdenaro.lom.entity.Turret;

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
				Turret.instance.createTurret(player.getLocation());
			}
			if("slow".equals(command.getName()))
			{
				if(args.length != 2)
				{
					return false;
				}
				double percent = new Double(args[0]);
				int duration = new Integer(args[1]);
				LeagueOfMinecraft.instance.cc.slow(player,percent,duration);
			}
		}
		return true;
	}
	
}
