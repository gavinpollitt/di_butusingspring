This uses spring as the DI framework rather than Guice.

This has the following ramifications:
The original JSR-330 code remains relatively unchanged apart from exposing a SpringBeans using @Component where possible.

The only @Bean driven code is to create the specifics for each run such as number of dice, result processor...etc. And to supply the list of stat processors and the loggers.


