package me.bittnerdenaro.lom;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import me.bittnerdenaro.lom.champions.Champion;
import me.bittnerdenaro.lom.entity.Healthable;
import me.bittnerdenaro.lom.entity.Minion;
import me.bittnerdenaro.lom.entity.Turret;

import org.bukkit.ChatColor;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class LeagueOfMinecraft extends JavaPlugin
{
	public static LeagueOfMinecraft instance;
	public static enum Team{RED, BLUE};
	public static final int TPS = 20;
	public CrowdControl cc;
	public Map map;
	public HashMap<Player, Champion> playersChamp;
	public HashMap<LivingEntity, Minion> minions;
	public HashMap<LivingEntity, Turret> turrets;
	public HashMap<LivingEntity, Healthable> healthables;
	public LinkedList<Integer[]> waves;
	
	@Override
	public void onEnable()
	{
		loadHandlers();
		createConfig();
		loadConfig();
		loadCommands();
		waves = new LinkedList<Integer[]>();
		waves.push(new Integer[]{3,3,1});
		waves.push(new Integer[]{3,3,0});
		waves.push(new Integer[]{3,3,0});
		getServer().broadcastMessage(ChatColor.GREEN + "LeagueOfMinecraft enabled!");
		instance = this;
		playersChamp = new HashMap<Player, Champion>();
		minions = new HashMap<LivingEntity, Minion>();
		turrets = new HashMap<LivingEntity, Turret>();
		healthables = new HashMap<LivingEntity, Healthable>();
	}
	
	private void loadHandlers()
	{
		this.getServer().getPluginManager().registerEvents(new GeneralPlayerEvents(), this);
		cc = new CrowdControl();
		this.getServer().getPluginManager().registerEvents(cc, this);

	}
	
	public boolean sameTeam(Entity entity, Entity other)
	{
		Team team1 = null;
		Team team2 = null;
		if(playersChamp.containsKey(entity))
		{
			team1 = playersChamp.get(entity).team;
		}
		if(playersChamp.containsKey(other))
		{
			team2 = playersChamp.get(other).team;
		}
		
		if(minions.containsKey(entity))
		{
			team1 = minions.get(entity).team;
		}
		if(minions.containsKey(other))
		{
			team2 = minions.get(other).team;
		}
		
		if(turrets.containsKey(entity))
		{
			team1 = turrets.get(entity).team;
		}
		if(turrets.containsKey(other))
		{
			team2 = turrets.get(other).team;
		}
		
		return team1 == team2;
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
		
		ConfigurationSection teamSection = config.getConfigurationSection("teams");
		if(teamSection != null)
		{
			map = new Map(teamSection);
			new BukkitRunnable()
			{
				@Override
				public void run()
				{
					new BukkitRunnable()
					{
						int count = 0;
						int index = 0;
						@Override
						public void run()
						{
							if(count++ < waves.peek()[index])
							{
								Minion.spawnMinion(index);
							}
							else
							{
								index++;
								count = 0;
								if(index >= waves.peek().length)
								{
									waves.addLast(waves.pop());
									cancel();
								}
							}
						}
						
					}.runTaskTimer(instance, 1, TPS / 2);
				}
				
			}.runTaskTimer(this,1,TPS * 30);
			
		}
	}
	
	private void loadCommands()
	{
		CommandEvents commander = new CommandEvents();
		
		getCommand("create-turret").setExecutor(commander);
		getCommand("slow").setExecutor(commander);
		getCommand("knockup").setExecutor(commander);
		getCommand("knockback").setExecutor(commander);
	}
	
	public void addHandler( Listener listener )
	{
		this.getServer().getPluginManager().registerEvents(listener, this);
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
