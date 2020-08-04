package org.github.boziroland;

import org.github.boziroland.entities.Milestone;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Constants {

	private static final long SECONDS_IN_A_DAY = Duration.ofDays(1).getSeconds();
	public static long DATA_RETRIEVE_DELAY_IN_SECONDS = SECONDS_IN_A_DAY;
	public static long INITIAL_DATA_RETRIEVE_DELAY_IN_SECONDS = SECONDS_IN_A_DAY / 2;

	//TODO: Itt a milestone létjogosultságát nem igazán értem. Erről majd beszéljünk.
	private static Set<Milestone> milestones;

	public static void setMilestones(List<Milestone> m){
		if(milestones == null)
			milestones = new HashSet<>(m);
	}

	public static List<Milestone> getMilestonesAsList(){
		return new ArrayList<>(milestones);
	}

	public static Set<Milestone> getMilestonesAsSet(){
		return milestones;
	}

}
