package me.bittnerdenaro.lom;

import java.util.ArrayList;

import me.bittnerdenaro.lom.LeagueOfMinecraft.Team;

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
