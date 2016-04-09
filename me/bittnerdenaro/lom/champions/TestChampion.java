package me.bittnerdenaro.lom.champions;

import me.bittnerdenaro.lom.skills.TestSkill1;
import me.bittnerdenaro.lom.skills.TestSkill2;
import me.bittnerdenaro.lom.skills.TestSkill3;
import me.bittnerdenaro.lom.skills.TestSkill4;
import me.bittnerdenaro.lom.skills.TestSummonerSkill1;
import me.bittnerdenaro.lom.skills.TestSummonerSkill2;

import org.bukkit.entity.Player;

public class TestChampion extends Champion {

	public TestChampion(Player player)
	{
		super( player, 0, 0, 0, 0, 
				new TestSkill1(), new TestSkill2(), new TestSkill3(), new TestSkill4(),
				new TestSummonerSkill1(), new TestSummonerSkill2() );
	}

}
