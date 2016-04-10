package me.bittnerdenaro.lom.champions;

import java.util.HashMap;

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
	
	HashMap<String, Stat> statMap;
	
	//player info
	public Player player;
	public LeagueOfMinecraft.Team team;
	public Scoreboard board;
	
	//champion info
	public String name;	
	
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
		
		//init scoreboard
		this.board = createScoreBoard( health );
		Objective sideObjective = board.getObjective("Side");
		Objective nameObjective = board.getObjective("Name");
		
		statMap = new HashMap<String, Stat>();
		statMap.put("health", new Stat( health, nameObjective.getScore("Health:"), -1 ));
		statMap.put("maxHealth", new Stat( health, null, 0));
		statMap.put("healthRegen", new Stat( healthRegen, sideObjective.getScore("Health Regen:"), 0 ));
		statMap.put("mana", new Stat( mana, sideObjective.getScore("Mana:"), -1 )) ;
		statMap.put("maxMana", new Stat( mana, null, 0));
		statMap.put("manaRegen", new Stat( manaRegen, nameObjective.getScore("Mana Regen:"), 0 ));
		statMap.put("range", new Stat( range, sideObjective.getScore("Range:"), 0 )) ;
		statMap.put("attackDamage", new Stat( attackDamage, sideObjective.getScore("Attack Damage:"), 7 ));
		statMap.put("armor", new Stat( armor, sideObjective.getScore("Armor:"), 5 )) ;
		statMap.put("magicResist", new Stat( magicResist, sideObjective.getScore("Magic Resist:"), 4 ));
		statMap.put("moveSpeed", new Stat( moveSpeed, sideObjective.getScore("Move Speed:"), 3 )) ;
		statMap.put("attackSpeed", new Stat( attackSpeed, sideObjective.getScore("Attack Speed:"), 2 ));
		
		statMap.put("abilityPower", new Stat( (double) 0, sideObjective.getScore("Ability Power:"), 6 )) ;
		statMap.put("armorPenetration", new Stat( (double) 0, sideObjective.getScore("Armor Penetration:"), 0 ));
		statMap.put("magicPenetration", new Stat( (double) 0, sideObjective.getScore("Magic Penetration:"), 0 )) ;
		statMap.put("cooldownReduction", new Stat( (double) 0, sideObjective.getScore("Cooldown Reduction:"), 0 ));
		statMap.put("critChance", new Stat( (double) 0, sideObjective.getScore("Critical Chance:"), 0 )) ;
		statMap.put("spellVamp", new Stat( (double) 0, sideObjective.getScore("Spell Vampirism:"), 0 ));
		statMap.put("lifeSteal", new Stat( (double) 0, sideObjective.getScore("Life Steal:"), 0 )) ;
		statMap.put("tenacity", new Stat( (double) 0, sideObjective.getScore("Tenacity:"), 0 ));
		
		statMap.put("level", new Stat( (double) 1, sideObjective.getScore("Level:"), 0 )) ;
		statMap.put("experience", new Stat( (double) 0, sideObjective.getScore("Experience:"), 0 )) ;
		statMap.put("gold", new Stat( (double) 0, sideObjective.getScore("Gold:"), 8 ));
		
		this.skill1 = skill1;
		this.skill2 = skill2;
		this.skill3 = skill3;
		this.skill4 = skill4;
		this.summonerSkill1 = summonerSkill1;
		this.summonerSkill2 = summonerSkill2;
		
		statMap.put("skill1", new Stat( this.skill1.getCooldown(), sideObjective.getScore(this.skill1.name + ":"), 14));
		statMap.put("skill2", new Stat( this.skill2.getCooldown(), sideObjective.getScore(this.skill2.name + ":"), 13));
		statMap.put("skill3", new Stat( this.skill3.getCooldown(), sideObjective.getScore(this.skill3.name + ":"), 12));
		statMap.put("skill4", new Stat( this.skill4.getCooldown(), sideObjective.getScore(this.skill4.name + ":"), 11));
		statMap.put("summonerSkill1", new Stat( this.summonerSkill1.getCooldown(), sideObjective.getScore(this.summonerSkill1.name + ":"), 10));
		statMap.put("summonerSkill2", new Stat( this.summonerSkill2.getCooldown(), sideObjective.getScore(this.summonerSkill2.name + ":"), 9));
		
		for( Stat s: statMap.values())
		{
			if( s.order > 0 || s.order < 0 )
			{
				s.update(this.board, sideObjective);
			}
			/*else if( s.order < 0 )
			{
				s.update(this.board, nameObjective);
			}*/
		}
		Score namescore = nameObjective.getScore( this.player.getName());
		namescore.setScore( (int)statMap.get("health").value );
		
		this.player.sendMessage(""+ statMap.get("health").value);
		
		this.player.setLevel(1);
		
		
		initSpells();
		
		
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

	private Scoreboard createScoreBoard( double maxHealth ) 
	{
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		//Team boardTeam = board.registerNewTeam( this.team.toString() );
		Objective sideObjective = board.registerNewObjective("Side", "dummy");
		sideObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
		sideObjective.setDisplayName(this.name);
		
		Objective nameObjective = board.registerNewObjective("Name", "dummy");
		nameObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		nameObjective.setDisplayName("/ " + maxHealth);
		
		return board;
	}
	
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
				switch(slot)
				{
					case 0:
						this.skill1.use(event);
						this.player.getInventory().setItem(0, this.player.getInventory().getItem(0));
						break;
					case 1:
						this.skill2.use(event);
						this.player.getInventory().setItem(1, this.player.getInventory().getItem(1));
						break;
					case 2:
						this.skill3.use(event);
						this.player.getInventory().setItem(2, this.player.getInventory().getItem(2));
						break;
					case 3:
						this.skill4.use(event);
						this.player.getInventory().setItem(3, this.player.getInventory().getItem(3));
						break;
					case 4:
						this.summonerSkill1.use(event);
						this.player.getInventory().setItem(4, this.player.getInventory().getItem(4));
						break;
					case 5:
						this.summonerSkill2.use(event);
						this.player.getInventory().setItem(5, this.player.getInventory().getItem(5));
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
			while(loc.distance(pos) < statMap.get("range").value / 100)
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
