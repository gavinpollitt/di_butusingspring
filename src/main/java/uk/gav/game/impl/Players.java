package uk.gav.game.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import uk.gav.game.logging.Logger;

public class Players {
	private final List<Player> players;
		
	@Inject
	public Players(final @Named("players") Set<String> players, @Named("system") final Logger logger) {
		super();
		this.players = players.stream().map(Player::new).collect(Collectors.toList());
		logger.log(this.getClass() + "->" + super.toString());
	}

	public String getPlayerName(final int p) {
		return players.get(p-1).name;
	}
	
	public int getPlayerWon(final int p) {
		return players.get(p-1).won;		
	}

	public void won(final int p) {
		players.get(p-1).won+=1;
	}
	
	public String toString() {
		return this.players.stream().map(p -> p.toString()).reduce("", (a, v) -> a + v + "\n");
	}
	
	private static class Player {
		private String name;
		private int won = 0;
		
		public Player(final String name) {
			this.name = name;
		}
		
		public String toString() {
			return name + " won " + won + (won==1?" game":" games");
		}
	}
}
