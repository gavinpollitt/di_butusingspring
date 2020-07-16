package uk.gav.game.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import uk.gav.game.GameResultProcessor;
import uk.gav.game.logging.Logger;

/**
 * 
 * @author regen
 *
 * Result processor instance based solely on the player with the highest total from their roles.
 */
@Singleton
public class HighestRollResultProcessor implements GameResultProcessor {

	@Inject
	@Named("game")
	private Logger gameLogger;

	@Inject
	public HighestRollResultProcessor(@Named("system") final Logger logger) {
		logger.log(this.getClass() + "->" + this);
	}
	
	@Override
	public String processResult(final Players players, final List<Integer> player1, final List<Integer> player2) {
		if (gameLogger != null) gameLogger.log("Highest Score Wins");
		int p1Score = player1.stream().reduce(0, (a, i) -> a + i);
		int p2Score = player2.stream().reduce(0, (a, i) -> a + i);
		
		if (p1Score > p2Score) {
			players.won(1);
			return players.getPlayerName(1) + " with " + p1Score + " beats " + players.getPlayerName(2) + " with " + p2Score;
		}
		else if (p2Score > p1Score) {
			players.won(2);
			return players.getPlayerName(2) + " with " + p2Score + " beats " + players.getPlayerName(1) + " with " + p1Score;
		}
		else {
			return "Both players rolled: " + p1Score + ". It's a draw";			
		}
	}

}
