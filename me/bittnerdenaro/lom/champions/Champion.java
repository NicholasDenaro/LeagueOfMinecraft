package me.bittnerdenaro.lom.champions;

import java.util.List;

import me.bittnerdenaro.lom.BDProjectile;
import me.bittnerdenaro.lom.LeagueOfMinecraft;
import me.bittnerdenaro.lom.skills.Skill;
import net.minecraft.server.v1_9_R1.Tuple;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

public abstract class Champion implements Listener{
	
	//player info
	public Player player;
	public LeagueOfMinecraft.Team team;
	public Scoreboard board;
	
	//champion info
	public String name;

	public double health;
	public double healthRegen;
	public double mana;
	public double manaRegen;
	public double range;
	public double attackDamage;
	public double armor;
	public double magicResist;
	public double moveSpeed;
	public double attackSpeed;
	
	public double abilityPower;
	public double armorPenetration;
	public double magicPenetration;	
	public double cooldownReduction;
	public double critChance;
	public double lifeSteal;
	public double spellVamp;
	public double tenacity;
	
	public int level;
	public int experience;
	
	
	//skills
	public Skill skill1;
	public Skill skill2;
	public Skill skill3;
	public Skill skill4;
	public Skill summonerSkill1;
	public Skill summonerSkill2;
	
	
	public Champion ( Player player, LeagueOfMinecraft.Team team, String name, 
						double health, double healthRegen, double mana, double manaRegen, double range, 
						double attackDamage, double armor, double magicResist, double moveSpeed, double attackSpeed, 
						Skill skill1, Skill skill2, Skill skill3, Skill skill4,
						Skill summonerSkill1, Skill summonerSkill2 )
	{
		this.player = player;
		this.team = team;
		
		this.name = name;
		
		this.health = health;
		this.healthRegen = healthRegen;
		this.mana = mana;
		this.manaRegen = manaRegen;
		this.range = range;
		this.attackDamage = attackDamage;
		this.armor = armor;
		this.magicResist = magicResist;
		this.moveSpeed = moveSpeed;
		this.attackSpeed = attackSpeed;
		
		
		this.abilityPower = 0;
		this.armorPenetration = 0;
		this.magicPenetration = 0;
		this.cooldownReduction = 0;
		this.critChance = 0;
		this.spellVamp = 0;
		this.lifeSteal = 0;
		this.tenacity = 0;

		this.level = 1;
		this.experience = 0;
		
		this.skill1 = skill1;
		this.skill2 = skill2;
		this.skill3 = skill3;
		this.skill4 = skill4;
		this.summonerSkill1 = summonerSkill1;
		this.summonerSkill2 = summonerSkill2;
		
		initSpells();
		
		//init scoreboard
		this.board = createScoreBoard();
		
		//set scoreboard
		this.player.setScoreboard(this.board);
		
		this.player.sendMessage("You are " + this.name);
		
		
		LeagueOfMinecraft.instance.addHandler(this);
	}
	
	private void initSpells() {
		ItemStack q = new ItemStack(Material.EGG);
		ItemStack w = new ItemStack(Material.EGG);
		ItemStack e = new ItemStack(Material.EGG);
		ItemStack r = new ItemStack(Material.EGG);
		ItemStack d = new ItemStack(Material.EGG);
		ItemStack f = new ItemStack(Material.EGG);
		q = setName(q, this.skill1.name);
		w = setName(w, this.skill2.name);
		e = setName(e, this.skill3.name);
		r = setName(r, this.skill4.name);
		d = setName(d, this.summonerSkill1.name);
		f = setName(f, this.summonerSkill2.name);
		this.player.getInventory().setItem(0,q);
		this.player.getInventory().setItem(1,w);
		this.player.getInventory().setItem(2,e);
		this.player.getInventory().setItem(3,r);
		this.player.getInventory().setItem(4,d);
		this.player.getInventory().setItem(5,f);
	}
	
	//credit: drampelt, https://bukkit.org/threads/how-to-change-items-names.118126/
	public ItemStack setName(ItemStack is, String name){
        ItemMeta m = is.getItemMeta();
        m.setDisplayName(name);
        is.setItemMeta(m);
        return is;
    }

	private Scoreboard createScoreBoard() 
	{
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		//Team boardTeam = board.registerNewTeam( this.team.toString() );
		Objective objective = board.registerNewObjective("Stats", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(this.name);
		Score health = objective.getScore("Health:");
		health.setScore((int)this.health);
		Score mana = objective.getScore("Mana:");
		mana.setScore((int)this.mana);
		Score skill1Score = objective.getScore(this.skill1.name);
		Score skill2Score = objective.getScore(this.skill2.name);
		Score skill3Score = objective.getScore(this.skill3.name);
		Score skill4Score = objective.getScore(this.skill4.name);
		Score summonerSkill1Score = objective.getScore(this.summonerSkill1.name);
		Score summonerSkill2Score = objective.getScore(this.summonerSkill2.name);
		skill1Score.setScore(this.skill1.getCooldown());
		skill2Score.setScore(this.skill2.getCooldown());
		skill3Score.setScore(this.skill3.getCooldown());
		skill4Score.setScore(this.skill4.getCooldown());
		summonerSkill1Score.setScore(this.summonerSkill1.getCooldown());
		summonerSkill2Score.setScore(this.summonerSkill2.getCooldown());		
		
		return board;
	}

	/*//Handle right click functions
	@EventHandler
	public void handleClickOnEntity( PlayerInteractEntityEvent event )
	{
		event.setCancelled(true);
		event.getPlayer().sendMessage("PIEE");
		rightClick(event);
	}
	
	@EventHandler
	public void handleClickOnEntityAtEntity( PlayerInteractAtEntityEvent event )
	{
		event.setCancelled(true);
		event.getPlayer().sendMessage("PIAEE");
		rightClick(event);
	}*/
	
	@EventHandler//have to use eggs (or at least sticks cause double event, other items may work)
	public void handleClickNotOnEntity( PlayerInteractEvent event )
	{
		event.setCancelled(true);
		if( event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK )
			rightClick(event);
		else
			leftClick(event.getPlayer());
	}
	
	@EventHandler//have to use eggs (or at least sticks cause double event, other items may work)
	public void handleClickOnEntity( EntityDamageByEntityEvent event )
	{
		event.setCancelled(true);
		if(event.getDamager() instanceof Player)
		{
			leftClick((Player)event.getDamager());
		}
	}
	
	public void rightClick( PlayerEvent event )
	{		
		Player player = event.getPlayer();
		if(LeagueOfMinecraft.instance.cc.canCast(player))
		{
			int slot = event.getPlayer().getInventory().getHeldItemSlot();
			if( event.getPlayer() == this.player )
			{
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

	public void leftClick(Player player)
	{
		//Player player = event.getPlayer();
		if( player == this.player )
		{
			Location loc = player.getLocation().clone();
			loc.setPitch(0);
			
			Vector dir = loc.getDirection().normalize().multiply(0.5);
			Location pos = loc.clone();
			//Tuple<Double, Score> val = statMap.get("range").b();
			
			List<Entity> entities = LeagueOfMinecraft.instance.getWorld().getEntities();
			while(loc.distance(pos) < range / 100)
			{
				loc.add(dir);
				LivingEntity closest = null;
				double distance = 0.5;
				for(Entity entity : entities)
				{
					if(entity instanceof LivingEntity && entity != player)
					{
						double dist = entity.getLocation().distance(loc);
						if(dist < distance)
						{
							closest = (LivingEntity)entity;
							distance = dist;
						}
					}
				}
				
				LivingEntity target = closest;
				
				if(target != null)
				{
					//targetted!
					player.sendMessage("basic attacked!");
					Vector projdir = target.getEyeLocation().clone().subtract(player.getEyeLocation()).toVector().normalize();
					Projectile proj = (Projectile)LeagueOfMinecraft.instance.getWorld().spawnEntity(player.getEyeLocation().clone().add(projdir.multiply(2)),EntityType.ARROW);
					new BDProjectile(player, proj, 0.3, target, new BukkitRunnable(){

						@Override
						public void run()
						{
							target.sendMessage("You got hit by a basic attack!");
						}
						
					});
					return;
				}
			}
		}
	}
}
