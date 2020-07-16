package uk.gav.game.stats;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import uk.gav.game.logging.Logger;

@Singleton
public class FaceCountStat implements StatProducer {

	@Inject
	public FaceCountStat(@Named("system") final Logger logger) {
		logger.log(this.getClass() + "->" + this);
	}
	
	@Override
	public String analyse(final int sides, final List<List<Integer>> gameDice) {
		
		List<Integer> allDice = StatProducer.flattenRolls(gameDice);
		
		Map<Integer, Integer> range = new TreeMap<>();
		IntStream.range(1, sides+1).forEach(i -> range.put(i, 0));
		
		allDice.stream().forEach(d -> range.put(d, range.get(d)+1));
		
		String output = range.keySet().stream().map(k -> ""+k).reduce("", (a, v) -> a.concat("[" + v + "," + range.get(Integer.parseInt(v)) + "]"));
		
		return output;
	}
	
	public static void main(String[] args) {
		List<Integer> one = Arrays.asList(3,5,2,3,1);
		List<Integer> two = Arrays.asList(3,3,3);
		List<Integer> three = Arrays.asList(1,2,4,5,6);
		
		List<List<Integer>> lists = Arrays.asList(one,two,three);
		
		System.out.println(new FaceCountStat(new Logger("TEST", true)).analyse(6, lists));
	}

}
