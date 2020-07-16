package uk.gav;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import uk.gav.config.GameConf;
import uk.gav.config.GameConf.GameContext;
import uk.gav.game.impl.DoubleUpResultProcessor;
import uk.gav.game.impl.Game;
import uk.gav.game.impl.HighestRollResultProcessor;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
    private AnnotationConfigApplicationContext context;

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Highest Role Game between HR One and HR Two");
		GameConf.GameContext gc = (GameContext)context.getBean(GameContext.class);
		gc.init(HighestRollResultProcessor.class, 12, 3, "HR One", "HR Two");
		Game g = (Game)context.getBean(Game.class);
		System.out.println(g.play());
		System.out.println(g.play());
		System.out.println(g.play());
		System.out.println(g.getGameScores());

		System.out.println("-------------------------------------------------------------------------\n");
		
		System.out.println("Double Up Game between DU One and DU Two");
		gc = (GameContext)context.getBean(GameContext.class);  // Not really needed as a singleton, but just in case!
		gc.init(DoubleUpResultProcessor.class, 6, 4, "DU One", "DU Two");
		g = (Game)context.getBean(Game.class);
		System.out.println(g.play());
		System.out.println(g.play());
		System.out.println(g.play());
		System.out.println(g.play());
		System.out.println(g.play());
		System.out.println(g.getGameScores());
    }
}
