package uk.gav.game;

import java.util.List;

import uk.gav.game.impl.Players;

/**
 * 
 * @author regen
 *
 * Base interface for classes that will take the rolls for each player and determine the result based on the requirements of
 * the particular game being played.
 */
public interface GameResultProcessor {

	/**
	 * @param players Holder for the player names
	 * @param player1 A list of integer values containing the scores rolled by player 1. 
	 * @param player2 A list of integer values containing the scores rolled by player 1. 
	 * @return The result details in the required readable format
	 */
	public String processResult(final Players players, final List<Integer> player1, final List<Integer> player2);
}
