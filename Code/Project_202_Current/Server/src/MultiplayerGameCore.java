package multiplayergameServer;

import java.util.*;

public class MultiplayerGameCore
{
	int roundLeft, round;
	List<Player> playerList = new ArrayList<Player>();
	GameStateManager gameStateManager = new GameStateManager();
	Timer timer = new Timer();

	private static MultiplayerGameCore instance = null;

	private MultiplayerGameCore()
	{
	}

	public static synchronized MultiplayerGameCore getInstance()
	{
		if (instance == null)
		{
			instance = new MultiplayerGameCore();
		}
		return instance;
	}

	public Boolean addPlayer(Player newPlayer)
	{

		for (int i = 0; i < playerList.size(); i++)
		{
			if (playerList.get(i).getName().equalsIgnoreCase(newPlayer.getName()))
				return false;
		}

		playerList.add(newPlayer);
		gameStateManager.attach(newPlayer);
		newPlayer.setSubject(gameStateManager);
		return true;
	}

	public Boolean removePlayer(Player p)
	{
		for (int i = 0; i < playerList.size(); i++)
		{
			if (playerList.get(i).getName().equalsIgnoreCase(p.getName()))
			{
				playerList.remove(i);
				gameStateManager.detach(p);
				return true;
			}
		}

		return false;
	}

	public Boolean removeAllPlayer()
	{
		for (int i = 0; i < playerList.size(); i++)
		{
			gameStateManager.detach(playerList.get(i));
		}
		playerList.clear();
		return true;
	}

	public Boolean updatePlayerData(Player p)
	{
		for (int i = 0; i < playerList.size(); i++)
		{
			if (playerList.get(i).getName().equalsIgnoreCase(p.getName()))
			{
				playerList.get(i).setRoundStatus(p.getRoundStatus());
				playerList.get(i).setScore(p.getScore());
				// int nCurrScore = playerList.get(i).getScore();
				// p.setScore(nCurrScore);
				// All other data already came from client. Otherwise fill in
				// here.
				// playerList.set(i, p);
				return true;
			}
		}
		return false;
	}

	public List<Player> getListofPlayer()
	{
		return playerList;
	}

	public void startServer()
	{
		timer.schedule(gameStateManager, 3000, 3000);
	}

	public void setRound(int round)
	{
		this.round = round;
		this.roundLeft = round;
	}

	public void setRoundDone()
	{
		roundLeft--;
	}

	public int getRound()
	{
		return (round - roundLeft) + 1;
	}

	public int getRoundLeft()
	{
		return roundLeft;
	}
}