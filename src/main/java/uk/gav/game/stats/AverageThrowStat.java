package uk.gav.game.stats;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import uk.gav.game.logging.Logger;

@Singleton
public class AverageThrowStat implements StatProducer {

	@Inject
	public AverageThrowStat(@Named("system") final Logger logger) {
		logger.log(this.getClass() + "->" + this);
	}
	
	@Override
	public String analyse(final int sides, final List<List<Integer>> gameDice) {
		
		List<Integer> allDice = StatProducer.flattenRolls(gameDice);
		
		Double av = allDice.stream().reduce(0, (a,v) -> a+v)/(allDice.size()*1.0);

		return "Average Role: " + av;
	}
	
	public static void main(String[] args) {
		List<Integer> one = Arrays.asList(3,5,2,3,1,1);
		List<Integer> two = Arrays.asList(3,3,3);
		List<Integer> three = Arrays.asList(1,2,4,5,6);
		
		List<List<Integer>> lists = Arrays.asList(one,two,three);
		
		System.out.println(new AverageThrowStat(new Logger("TEST", true)).analyse(6, lists));
	}
}
