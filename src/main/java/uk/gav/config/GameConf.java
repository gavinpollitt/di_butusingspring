package uk.gav.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import uk.gav.game.GameResultProcessor;
import uk.gav.game.annotation.Dice;
import uk.gav.game.annotation.Sides;
import uk.gav.game.impl.BasicDiceProvider;
import uk.gav.game.impl.DoubleUpResultProcessor;
import uk.gav.game.impl.Game;
import uk.gav.game.impl.GameData;
import uk.gav.game.impl.HighestRollResultProcessor;
import uk.gav.game.impl.Players;
import uk.gav.game.logging.Logger;
import uk.gav.game.stats.AverageThrowStat;
import uk.gav.game.stats.FaceCountStat;
import uk.gav.game.stats.StatProducer;

@Configuration
@ConfigurationProperties(prefix = "logging")
public class GameConf {
    private boolean systemLogging;
    private boolean gameLogging;
    private boolean statisticLogging;

    @Autowired
    private AnnotationConfigApplicationContext context;
    
    @PostConstruct
    public void init() {
        
        context.registerBean(BasicDiceProvider.class, bd -> bd.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE));
        context.registerBean("uk.gav.game.impl.Game", Game.class, bd -> bd.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE));
        context.registerBean(GameData.class, bd -> bd.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE));
        context.registerBean("uk.gav.game.impl.Players", Players.class, bd -> bd.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE));
        context.registerBean(AverageThrowStat.class);
        context.registerBean(FaceCountStat.class);
    }

    @Bean
    @Scope("prototype")
    public GameResultProcessor resultProcessor(@Qualifier("resultProcessorClass") final Class<? extends GameResultProcessor> type, @Qualifier("system") final Logger sl) {
        if (type == HighestRollResultProcessor.class) {
            return new HighestRollResultProcessor(sl);
        }
        else {
            return new DoubleUpResultProcessor(sl);
        }
    }

    //@Bean  Probably not required as Spring will do automatically
    // public List<StatProducer> statProducers() {
    //     return Arrays.asList(averageThrowStat(), faceCountStat());
    // }

    @Bean(name = "system")
    public Logger systemLogger() {
        return new Logger("system", this.systemLogging);
    }

    @Bean(name = "game")
    public Logger gameLogger() {
        return new Logger("game", this.gameLogging);
    }

    @Bean(name = "statistic")
    public Logger statisticLogger() {
        return new Logger("statistic", this.statisticLogging);
    }

    public boolean isSystem() {
        return systemLogging;
    }

    public void setSystem(boolean systemLogging) {
        this.systemLogging = systemLogging;
    }

    public boolean isGame() {
        return gameLogging;
    }

    public void setGame(boolean gameLogging) {
        this.gameLogging = gameLogging;
    }

    public boolean isStatistic() {
        return statisticLogging;
    }

    public void setStatistic(boolean statisticLogging) {
        this.statisticLogging = statisticLogging;
    }

    
    //Put appropriate beans below to prevent CGLIB interference, i.e. Integers can be exposed directly as beans
    //rather than CGLIB trying to proxy, which it can't with Integers due to them being immutable.

    /**
     * This is effectively the bean to hold the current game 'environment' data. It can be adjusted before
     * a new game is started.
     */
    @Component
    public static class GameContext {
        private  Class<? extends GameResultProcessor> type;
        private  int sides;
        private  int dice;
        private  Set<String> players;

        public void init(Class<? extends GameResultProcessor> type, int sides, int dice, String ... players) {
            this.type = type;
            this.sides = sides;
            this.dice = dice;
            this.players = new HashSet<>(Arrays.asList(players));
        }

        @Bean(name="resultProcessorClass")
        @Scope("prototype")
        public Class<? extends GameResultProcessor> getType() {
            return this.type;
        }

        @Bean
        @Scope("prototype")
        @Sides
        public Integer getSides() {
            return this.sides;
        }

        @Bean
        @Scope("prototype")
        @Dice
        public int getDice() {
            return this.dice;
        }

        @Bean(name="players")
        @Scope("prototype")
        public Set<String> getPlayers() {
            return this.players;
        }
    }

 }