package uk.gav.game.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import uk.gav.game.Die;
import uk.gav.game.GameResultProcessor;
import uk.gav.game.logging.Logger;
import uk.gav.game.stats.StatProducer;

/**
 * 
 * @author regen
 *
 * A single iteration of the current 'game of dice'. The type of game will be determined by the injected classes
 */
public class Game {
	
	private final GameData context;
	
	private final GameResultProcessor resultProcessor;
	
	private final Set<StatProducer> statProducers;
	
	@Inject
	@Named("game")
	private Logger gameLogger;
	
	@Inject
	@Named("statistic")
	private Logger statLogger;
	
	private Logger systemLogger;
	
	/**
	 * 
	 * @param context The context of the game, i.e. the number of dice...etc
	 * @param resultProcessor Interpreter of the results of this game to determine the result.
	 */
	@Inject
	public Game(final GameData context, 
				final GameResultProcessor resultProcessor, 
				final Set<StatProducer> statProducers,
				@Named("system") final Logger systemLogger ) {
		this.context = context;
		this.resultProcessor = resultProcessor;
		this.statProducers = statProducers;
		this.systemLogger = systemLogger;
		this.systemLogger.log(this.getClass() + "->" + this);
	}
	
	/**
	 * Generic play of the game delegating specifics to context and result process
	 * @return the textual result of the game
	 */
	public String play() {
		if (gameLogger != null) this.gameLogger.log(this.context.toString());
		List<Die> dice = this.context.getDice();
		
		List<Integer> p1Result = dice.stream().map(Die::roll).collect(Collectors.toList());
		List<Integer> p2Result = dice.stream().map(Die::roll).collect(Collectors.toList());

		if (this.statProducers != null) {
			this.statProducers.stream().map(p -> p.analyse(this.context.getSides(), StatProducer.listUp(p1Result, p2Result))).forEach(statLogger::log);
		}
		
		return this.resultProcessor.processResult(this.context.getPlayers(), p1Result, p2Result);
	}

	public String getGameScores() {
		return this.context.getPlayers().toString();
	}
}
