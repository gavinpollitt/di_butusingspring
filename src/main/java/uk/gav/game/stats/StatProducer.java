package uk.gav.game.stats;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface StatProducer {
	public String analyse(final int sides, final List<List<Integer>> gameDice);
	
	static List<Integer> flattenRolls(final List<List<Integer>> rolls) {
		return rolls.stream().flatMap(Collection::stream).collect(Collectors.toList());

	}
	
	@SafeVarargs
	static List<List<Integer>> listUp(final List<Integer>... lists) {
		return Arrays.asList(lists);
	}
}
