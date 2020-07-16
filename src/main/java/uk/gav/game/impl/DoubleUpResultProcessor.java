package uk.gav.game.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import uk.gav.game.GameResultProcessor;
import uk.gav.game.logging.Logger;

/**
 * 
 * @author regen
 *
 * Singleton processor that will return the results of a single 'double-up' games round.
 */
@Singleton
public class DoubleUpResultProcessor implements GameResultProcessor {

	@Inject
	@Named("game")
	private Logger gameLogger;


	@Inject
    public DoubleUpResultProcessor(@Named("system") final Logger logger) {
		logger.log(this.getClass() + "->" + this);
	}
	/**
	 * Based on the numbers rolled, the total result is calculated as:
	 * 		Total for each distinct number rolled -> number of times rolled * face value * number of times rolled
	 */
	@Override
	public String processResult(final Players players, final List<Integer> player1, final List<Integer> player2) {
		if (gameLogger != null) gameLogger.log("Doubles count double and so on");
		Map<Integer,Integer> p1Rolls = checkDoubles(player1);
		Map<Integer,Integer> p2Rolls = checkDoubles(player2);
		
		String p1Db = reportDoubles(p1Rolls);
		String p2Db = reportDoubles(p2Rolls);
		
		int p1Score = total(p1Rolls);
		int p2Score = total(p2Rolls);
		
		if (p1Score > p2Score) {
			players.won(1);
			return players.getPlayerName(1) + " " + p1Db + " giving score " + p1Score + " beats " + players.getPlayerName(2)  + " who " + p2Db + " giving score "+ p2Score;
		}
		else if (p2Score > p1Score) {
			players.won(2);
			return players.getPlayerName(2) + " " + p2Db + " giving score " + p2Score + " beats " + players.getPlayerName(1)  + " who " + p1Db + " giving score "+ p1Score;
		}
		else {
			return "Both players rolled: " + p1Score + ". It's a draw";			
		}
	}
	
	/**
	 * 
	 * @param p the integers representing the rolls on the dice
	 * @return The map of r -> c   where r is the number rolled and c is the number of times rolled.
	 */
	private static Map<Integer,Integer> checkDoubles(final List<Integer> p) {
		Map<Integer,Integer> out = new HashMap<>();
		
		for (Integer i: p) {
			Integer cnt = out.get(i);
			
			out.put(i, cnt==null?1:cnt+1);
		}
		
		return out;
	}
	
	/**
	 * 
	 * @param dbs The map of r -> c   where r is the number rolled and c is the number of times rolled.
	 * @return The total score for this role.
	 */
	private static int total(final Map<Integer,Integer> dbs) {
		int tot = 0;
		for (Integer r: dbs.keySet()) {
			Integer m = dbs.get(r);
			tot += (m*m*r);
		}

		return tot;
	}
	
	/**
	 * 
	 * @param dbs The map of r -> c   where r is the number rolled and c is the number of times rolled.
	 * @return The prinatable version of the result.
	 */
	private static String reportDoubles(final Map<Integer,Integer> dbs) {
		String out = "rolled ";
		
		String sep = "";
		for (Integer r: dbs.keySet()) {
			Integer m = dbs.get(r);
			if (m > 1) {
				out += sep + (m + "X" + r);
				out += " = " + (m*m*r);
				sep = " and ";
			}
		}

		if (sep.length() == 0) {
			out+= "no doubles";
		}
		
		return out;
	}

	public static void main(String[] args) {
		List<Integer> check = Arrays.asList(1,2,3,4,2,5,5,2);
		System.out.println(checkDoubles(check));
		System.out.println(total(checkDoubles(check)));
		System.out.println(reportDoubles(checkDoubles(check)));
	}
}
