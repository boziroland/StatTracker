package org.github.boziroland.services;

import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import org.github.boziroland.entities.LeagueData;
import org.github.boziroland.repositories.ILeagueRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface ILeagueService defines the performable CRUD operations on the LeaguePlayer class.
 */
public interface ILeagueService extends IAPIService {

	/**
	 * Creates a LeagueData and passes it to
	 * @see ILeagueRepository#save(Object)
	 * @param player         The League account of the player
	 * @param lastTenMatches The last ten matches of the player
	 * @return The saved Leaguedata instance
	 */
	LeagueData createOrUpdate(Summoner player, List<MatchReference> lastTenMatches);

	/**
	 * Finds a
	 * @see LeagueData by its id
	 * @param id the id of the LeagueData
	 */
	Optional<LeagueData> findById(int id);

	/**
	 * Lists every stored LeagueData
	 */
	List<LeagueData> list();

	/**
	 * Deletes a
	 * @see LeagueData by its id
	 * @param id the id of the leagueData to remove
	 */
	void deleteById(int id);

}
