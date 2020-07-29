package org.github.boziroland;

import java.time.Duration;

public class Constants {

	private static final long SECONDS_IN_A_DAY = Duration.ofDays(1).getSeconds();
	public static final long DATA_RETRIEVE_DELAY_IN_SECONDS = SECONDS_IN_A_DAY;
}
