package uk.gav.game.logging;

import javax.inject.Singleton;

@Singleton
public class Logger {
	private final String type;
	private boolean active = false;
	
	public Logger(final String type, final boolean active) {
		this.type = type;
		this.active = active;
	}
	
	public void log(final String message) {
		if (active) {
			System.out.println(this.type + "->" + message);
		}
	}
}
