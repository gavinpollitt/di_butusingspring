package uk.gav.game.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import uk.gav.game.DiceProvider;
import uk.gav.game.Die;
import uk.gav.game.logging.Logger;

/**
 * 
 * @author regen
 *
 * Singleton holder of the current game environment allow injections of the provider of the dice and
 * the number of dice to use.
 */
public final class GameData {
	
	private final DiceProvider provider;
	
	private final List<Die> dice;
	
	private Logger logger;
	
	private final Players players;
	
	@Inject
	public GameData(final DiceProvider provider, final Players players, @Named("system") Logger logger) {
		this.provider = provider;
		this.logger = logger;
		this.dice = provider.get();
		this.players = players;
		this.logger.log(this.getClass() + "->" + super.toString());
	}
	
	public List<Die> getDice() {
		return this.dice;
	}
	
	public String toString() {
		String pl = this.dice.size() > 1?"dice":"die";
		return "Playing with " + this.provider.getDice() + " x " + this.getSides() + " sided " + pl;
	}
	
	public int getSides() {
		return this.provider.getSides();
	}

	public Players getPlayers() {
		return players;
	}
	
}
