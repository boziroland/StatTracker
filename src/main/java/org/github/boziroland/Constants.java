package org.github.boziroland;

import java.time.Duration;

public class Constants {

	private static final long SECONDS_IN_A_DAY = Duration.ofDays(1).getSeconds();
	public static long DATA_RETRIEVE_MIN_DELAY_IN_SECONDS = 10;
	public static long DATA_RETRIEVE_MAX_DELAY_IN_SECONDS = SECONDS_IN_A_DAY - 1;//30
	public static long INITIAL_DATA_RETRIEVE_DELAY_IN_SECONDS = 5;

	public static final boolean SEND_EMAIL_DEFAULT_VALUE = true;
	public static final boolean PROFILE_PUBLIC_DEFAULT_VALUE = true;
}