package uk.gav.game.impl;

import uk.gav.game.Die;

/**
 * 
 * @author regen
 *
 * A basic Die that is initialised with the number of sides and produces results based on a random number
 * limited to this number of sides.
 */
public class BasicDie implements Die{
	private final int sides;
	
	public BasicDie(final Integer sides) {
		this.sides = sides;		
	}
	
	public int roll() {
		return (int)(Math.random()*this.sides) + 1;
	}
}
