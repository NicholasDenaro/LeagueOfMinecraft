package me.bittnerdenaro.lom.champions;

import me.bittnerdenaro.lom.LeagueOfMinecraft.Team;
import me.bittnerdenaro.lom.skills.Skill;
import me.bittnerdenaro.lom.skills.TestSkill1;

import org.bukkit.entity.Player;

public class Ashe extends Champion {

	public Ashe( Player player, Team team ) {
		super(player, team, "Ashe", 527.72, 5.42, 280, 6.97, 600,
				56.51, 21.212, 30, 325, .658, new TestSkill1(),
				new TestSkill1(), new TestSkill1(), new TestSkill1(), 
				new TestSkill1(), new TestSkill1());
		// TODO Auto-generated constructor stub
	}

}
