package me.bittnerdenaro.lom;

import java.util.ArrayList;

import me.bittnerdenaro.lom.LeagueOfMinecraft.Team;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class Map
{
	public Location redNexus;
	public Location blueNexus;
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
