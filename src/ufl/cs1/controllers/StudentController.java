package ufl.cs1.controllers;

import game.controllers.DefenderController;
import game.controllers.benchmark.Devastator;
import game.models.Attacker;
import game.models.Defender;
import game.models.Game;
import game.models.Node;

import java.util.List;

public final class StudentController implements DefenderController
{
	public void init(Game game) { }

	public void shutdown(Game game) { }

	public int[] update(Game game,long timeDue)
	{
		int[] actions = new int[Game.NUM_DEFENDER];
		List<Defender> enemies = game.getDefenders();
		Defender red = enemies.get(0);
		Defender pink = enemies.get(1);
		Defender orange = enemies.get(2);
		Defender blue = enemies.get(3);
		
		//Chooses a random LEGAL action if required. Could be much simpler by simply returning
		//any random number of all of the ghosts

		List<Integer> possibleDirs0 = red.getPossibleDirs();
		List<Integer> possibleDirs1 = pink.getPossibleDirs();
		List<Integer> possibleDirs2 = orange.getPossibleDirs();
		List<Integer> possibleDirs3 = blue.getPossibleDirs();
		List<Node> powerPills = game.getPowerPillList();
		Node closestPowerPill = null;
		int distance = 10000;
		List<Node> pills = game.getPillList();
		for (int i = 0; i < powerPills.size(); ++i)
		{
			if (game.getAttacker().getLocation().getPathDistance(powerPills.get(i)) < distance)
			{
				closestPowerPill = powerPills.get(i);
				distance = game.getAttacker().getLocation().getPathDistance(closestPowerPill);
			}
		}
		if (possibleDirs0.size() != 0) //red dude
		{
			if (powerPills.size() != 0 && distance < 8)
				actions[0] = red.getNextDir(game.getAttacker().getLocation(), false);
			else
				actions[0] = red.getNextDir(game.getAttacker().getLocation(), true);
		}
		else
		{
			actions[0] = -1;
		}
		if (possibleDirs1.size() != 0)//pink dude
		{
			if (powerPills.size() != 0 && distance < 8)
				actions[1] = pink.getNextDir(game.getAttacker().getLocation(),true);
			else
				actions[1] = pink.getNextDir(game.getAttacker().getLocation(), true);
		}
		else
		{
			actions[1] = -1;
		}
		if (possibleDirs2.size() != 0)//orange dude
		{
			if (game.getAttacker().getLocation().getPathDistance(game.getDefender(3).getLocation()) <= 12)
				actions[2] = orange.getNextDir(game.getAttacker().getLocation(), true);
			else if (pills.size() < 150)
				actions[2] = orange.getNextDir(game.getAttacker().getLocation(),true);
			else
				actions[2] = orange.getNextDir(game.getAttacker().getLocation(), false);
		}
		else
		{
			actions[2] = -1;
		}
		if (possibleDirs3.size() != 0)//blue dude
		{
			if (game.getAttacker().getLocation().getPathDistance(game.getDefender(0).getLocation()) <= 10)
				actions[3] = blue.getNextDir(game.getAttacker().getLocation(), true);
			else
				actions[3] = blue.getNextDir(game.getAttacker().getLocation(), false);

		}
		else
		{
			actions[3] = -1;
		}

		return actions;
	}
}