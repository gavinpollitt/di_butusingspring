package uk.gav.game;

import java.util.List;

import javax.inject.Provider;

/**
 * 
 * @author regen
 *
 * Root interface of classes that act as Die factories.
 */
public interface DiceProvider extends Provider<List<Die>> {
	public int getSides();
	public int getDice();
}
