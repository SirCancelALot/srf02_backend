package com.tsystems.mms.evaluate.srf02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.logging.*;

@SpringBootApplication
@EnableWebMvc
public class Srf02Application {
	public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static final Handler HANDLER = new ConsoleHandler();
	public static final Formatter FORMATTER = new SimpleFormatter();

	public static void main(String[] args) {
		HANDLER.setLevel(Level.FINEST);
		HANDLER.setFormatter(FORMATTER);

		LOGGER.addHandler(HANDLER);
		LOGGER.setLevel(Level.FINEST);

		SpringApplication.run(Srf02Application.class, args);
	}
}
