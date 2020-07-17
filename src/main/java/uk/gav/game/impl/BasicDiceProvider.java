package uk.gav.game.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.inject.Inject;
import javax.inject.Named;

import uk.gav.game.DiceProvider;
import uk.gav.game.Die;
import uk.gav.game.annotation.Dice;
import uk.gav.game.annotation.Sides;
import uk.gav.game.logging.Logger;

/**
 * 
 * @author regen
 *
 * 'Factory' for basic dice that provide the standard roles based on the number of sides.
 */
public class BasicDiceProvider implements DiceProvider {
	
	protected final int sides;
	
	protected final int dice;
	
	/**
	 * The 'sides' parameter value can be injected by the DI framework
	 * @param sides The number of sides that the created dice will have
	 */
	@Inject
	public BasicDiceProvider(@Sides final Integer sides, final @Dice int dice, @Named("system") final Logger logger) {
		this.dice = dice;
		this.sides = sides;
		logger.log(this.getClass() + "->" + this);
	}

	@Override
	public List<Die> get() {
		List<Die> dList = new ArrayList<>(this.dice);
		IntStream.range(0, this.dice).forEach((i) -> dList.add(createDie())); 
		return dList;
	}
	
	
	@Override
	public int getSides() {
		return this.sides;
	}
	
	@Override
	public int getDice() {
		return this.dice;
	}
	
	protected Die createDie() {
		return new BasicDie(this.sides);
	}
}
