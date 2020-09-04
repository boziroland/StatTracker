package org.github.boziroland.services;

import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import org.github.boziroland.entities.LeagueData;
import org.github.boziroland.repositories.ILeagueRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface ILeagueService defines the performable CRUD operations on the
 *
 * @see LeagueData class.
 */
public interface ILeagueService extends IAPIService {

	/**
	 * Creates a LeagueData and passes it to
	 *
	 * @param player         The League account of the player
	 * @param lastTenMatches The last ten matches of the player
	 * @param username       The League username of the player
	 * @return The saved Leaguedata instance
	 * @see ILeagueRepository#save(Object)
	 */
	LeagueData createOrUpdate(Summoner player, List<MatchReference> lastTenMatches, String username);

	/**
	 * Finds a
	 *
	 * @param id the id of the LeagueData
	 * @see LeagueData by its id
	 */
	Optional<LeagueData> findById(int id);

	/**
	 * Lists every stored
	 *
	 * @see LeagueData
	 */
	List<LeagueData> list();

	/**
	 * Deletes a
	 *
	 * @param id the id of the LeagueData to remove
	 * @see LeagueData by its id
	 */
	void deleteById(int id);

}
