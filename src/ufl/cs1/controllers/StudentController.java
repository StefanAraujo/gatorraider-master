package ufl.cs1.controllers;

import game.controllers.DefenderController;
import game.controllers.benchmark.Devastator;
import game.models.Attacker;
import game.models.Defender;
import game.models.Game;
import game.models.Node;
import sun.invoke.empty.Empty;

import java.util.List;

public final class StudentController implements DefenderController
{
	public void init(Game game) { }

	public void shutdown(Game game) { }

	public int[] update(Game game,long timeDue)
	{
		int[] actions = new int[Game.NUM_DEFENDER];
		List<Defender> enemies = game.getDefenders();
		
		//Chooses a random LEGAL action if required. Could be much simpler by simply returning
		//any random number of all of the ghosts

		Defender red = enemies.get(0);
		List<Integer> possibleDirs0 = red.getPossibleDirs();

		if (red.isVulnerable()){
			actions[0] = red.getNextDir(game.getAttacker().getLocation(), false);
		}
		else {
			if (possibleDirs0.size() != 0)
				actions[0] = red.getNextDir(game.getAttacker().getLocation(), true);
			else
				actions[0] = -1;
		}

		Defender pink = enemies.get(1);
		List<Integer> possibleDirs1 = pink.getPossibleDirs();

		if (pink.isVulnerable()){
			actions[1] = pink.getNextDir(game.getAttacker().getLocation(), false);
		}
		else{
			if (possibleDirs1.size() != 0)
				actions[1] = pink.getNextDir(game.getAttacker().getLocation(), true);
			else
				actions[1] = -1;
		}

		Defender orange = enemies.get(2);
		List<Integer> possibleDirs2 = orange.getPossibleDirs();
		List<Node> powerpills = game.getPowerPillList();
		Node closest=null;
		int distance=10000;

		for (int i=0; i<powerpills.size();i++){
			if (game.getAttacker().getLocation().getPathDistance(powerpills.get(i))<distance){
				closest = powerpills.get(i);
				distance = game.getAttacker().getLocation().getPathDistance(powerpills.get(i));
			}
		}

		if (orange.isVulnerable()) {
			actions[2] = orange.getNextDir(game.getAttacker().getLocation(), false);
		}else {
			if (possibleDirs2.size() != 0 && powerpills.size() != 0)
				actions[2] = orange.getNextDir(closest, true);
			else
				actions[2] = -1;
		}

		return actions;

		}

	}
