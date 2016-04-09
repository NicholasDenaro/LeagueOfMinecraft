package me.bittnerdenaro.lom;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class LeagueOfMinecraft extends JavaPlugin
{
	public static LeagueOfMinecraft instance;
	@Override
	public void onEnable()
	{
		loadHandlers();
		getServer().broadcastMessage(ChatColor.GREEN + "LeagueOfMinecraft enabled!");
		instance = this;
	}
	
	private void loadHandlers()
	{
		this.getServer().getPluginManager().registerEvents(new GeneralPlayerEvents(), this);
	}
	
	@Override
	public void onDisable()
	{
		getServer().broadcastMessage(ChatColor.RED + "LeagueOfMinecraft disabled!");
	}
}
