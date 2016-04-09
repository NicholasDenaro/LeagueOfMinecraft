package me.bittnerdenaro.lom;

import java.io.File;
import java.util.Set;

import me.bittnerdenaro.lom.entity.Turret;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class LeagueOfMinecraft extends JavaPlugin
{
	public static LeagueOfMinecraft instance;
	public static final int TICKS = 20;
	
	@Override
	public void onEnable()
	{
		loadHandlers();
		createConfig();
		loadConfig();
		loadCommands();
		getServer().broadcastMessage(ChatColor.GREEN + "LeagueOfMinecraft enabled!");
		instance = this;
	}
	
	private void loadHandlers()
	{
		this.getServer().getPluginManager().registerEvents(new GeneralPlayerEvents(), this);
		this.getServer().getPluginManager().registerEvents(new Turret(), this);

	}
	
	private void createConfig()
	{
		File file = new File(getDataFolder(), "config.yml");
        if (!file.exists())
        {
            getLogger().info("config.yml not found, creating!");
            saveDefaultConfig();
        }
        else
        {
            getLogger().info("config.yml found, loading!");
        }
	}
	
	private void loadConfig()
	{
		FileConfiguration config = this.getConfig();
		
		ConfigurationSection turretSection = config.getConfigurationSection("turrets");
		if(turretSection != null)
		{
			Set<String> keys = turretSection.getKeys(false);
			for(String key : keys)
			{
				//load turrets
			}
		}
	}
	
	private void loadCommands()
	{
		CommandEvents commander = new CommandEvents();
		
		getCommand("create-turret").setExecutor(commander);
	}
	
	@Override
	public void onDisable()
	{
		getServer().broadcastMessage(ChatColor.RED + "LeagueOfMinecraft disabled!");
	}
	
	public World getWorld()
	{
		return getServer().getWorlds().get(0);
	}
}
