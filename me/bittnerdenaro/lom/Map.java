package me.bittnerdenaro.lom;

import java.util.ArrayList;
import java.util.Set;

import me.bittnerdenaro.lom.LeagueOfMinecraft.Team;
import me.bittnerdenaro.lom.entity.Turret;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class Map
{
	public Location redNexus;
	public Location blueNexus;
	public Location redJail;
	public Location blueJail;
	public Location redSpawn;
	public Location blueSpawn;
	public ArrayList<Location> redTurrets;
	public ArrayList<Location> blueTurrets;
	public ArrayList<Location> redInhibs;
	public ArrayList<Location> blueInhibs;
	
	public Map(ConfigurationSection section)
	{
		if(section.contains("RED.nexus"))
		{
			redNexus = (Location)section.get("RED.nexus");
		}
		if(section.contains("BLUE.nexus"))
		{
			blueNexus = (Location)section.get("BLUE.nexus");
		}
		if(section.contains("RED.jail"))
		{
			redJail = (Location)section.get("RED.jail");
		}
		if(section.contains("BLUE.jail"))
		{
			blueJail = (Location)section.get("BLUE.jail");
		}
		if(section.contains("RED.spawn"))
		{
			redSpawn = (Location)section.get("RED.spawn");
		}
		if(section.contains("BLUE.spawn"))
		{
			blueSpawn = (Location)section.get("BLUE.spawn");
		}
		if(section.contains("RED.turrets"))
		{
			Set<String> keys = section.getConfigurationSection("RED.turrets").getKeys(false);
			LeagueOfMinecraft.instance.getServer().broadcastMessage("number of keys: "+keys.size());
			for(String key : keys)
			{
				LeagueOfMinecraft.instance.getServer().broadcastMessage(ChatColor.GOLD + "key: " + key);
				Location loc = (Location)section.get("RED.turrets." + key);
				LeagueOfMinecraft.instance.getServer().broadcastMessage(ChatColor.RED + "-turret: " + loc);
				Turret.createTurret(loc,Team.RED);
			}
		}
		if(section.contains("BLUE.turrets"))
		{
			Set<String> keys = section.getConfigurationSection("BLUE.turrets").getKeys(false);
			for(String key : keys)
			{
				Location loc = (Location)section.get("BLUE.turrets." + key);
				Turret.createTurret(loc,Team.BLUE);
			}
		}
	}
	
	public Location getMyNexus(Team team)
	{
		switch(team)
		{
			case RED:
				return redNexus;
			case BLUE:
				return blueNexus;
			default:
				return redNexus;
		}
	}
	
	public Location getOpposingNexus(Team team)
	{
		switch(team)
		{
			case RED:
				return blueNexus;
			case BLUE:
				return redNexus;
			default:
				return redNexus;
		}
	}
}
