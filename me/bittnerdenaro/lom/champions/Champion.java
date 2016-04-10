package me.bittnerdenaro.lom.champions;

import me.bittnerdenaro.lom.LeagueOfMinecraft;
import me.bittnerdenaro.lom.skills.Skill;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class Champion implements Listener{
	
	Player player;
	private int health;
	private int mana;
	private int moveSpeed;
	private int attackSpeed;
	private int level;
	
	private Skill skill1;
	private Skill skill2;
	private Skill skill3;
	private Skill skill4;
	private Skill summonerSkill1;
	private Skill summonerSkill2;
	
	
	public Champion ( Player player, int health, int mana, int moveSpeed, int attackSpeed, 
						Skill skill1, Skill skill2, Skill skill3, Skill skill4,
						Skill summonerSkill1, Skill summonerSkill2 )
	{
		this.player = player;
		this.health = health;
		this.mana = mana;
		this.moveSpeed = moveSpeed;
		this.attackSpeed = attackSpeed;
		
		this.skill1 = skill1;
		this.skill2 = skill2;
		this.skill3 = skill3;
		this.skill4 = skill4;
		this.summonerSkill1 = summonerSkill1;
		this.summonerSkill2 = summonerSkill2;
		
		this.level = 1;
		
		LeagueOfMinecraft.instance.addHandler(this);
	}
	
	//Handle right click functions
	@EventHandler
	public void handleClickOnEntity( PlayerInteractEntityEvent event )
	{
		event.setCancelled(true);
		rightClick(event);
	}
	
	@EventHandler
	public void handleClickOnEntityAtEntity( PlayerInteractAtEntityEvent event )
	{
		event.setCancelled(true);
		rightClick(event);
	}
	
	@EventHandler
	public void handleClickNotOnEntity( PlayerInteractEvent event )
	{
		event.setCancelled(true);
		rightClick(event);
	}
	
	public void rightClick( PlayerEvent event )
	{		
		Player player = event.getPlayer();
		if(LeagueOfMinecraft.instance.cc.canCast(player))
		{
			int slot = event.getPlayer().getInventory().getHeldItemSlot();
			if( event.getPlayer() == this.player )
			switch( slot )
			{
				case 0:
					this.skill1.use(event);
					break;
				case 1:
					this.skill2.use(event);
					break;
				case 2:
					this.skill3.use(event);
					break;
				case 3:
					this.skill4.use(event);
					break;
				case 4:
					this.summonerSkill1.use(event);
					break;
				case 5:
					this.summonerSkill2.use(event);
					break;
				default:
					event.getPlayer().sendMessage("Invalid slot used - no spell here boys");				
			}
		}
	}

}
