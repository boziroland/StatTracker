package org.github.boziroland.services;

import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import org.github.boziroland.DAL.ILeagueDAO;
import org.github.boziroland.entities.LeagueData;

import java.util.List;
import java.util.Optional;

/**
 * The interface ILeagueService defines the performable CRUD operations on the LeaguePlayer class.
 */
public interface ILeagueService extends IAPIService {

	//TODO comment this class

	/**
	 * Creates a LeaguePlayer and passes it to
	 *
	 * @param player         The League account of the player
	 * @param lastTenMatches The last ten matches of the player
	 * @see ILeagueDAO#createOrUpdate(LeagueData)
	 */
	void createOrUpdate(Summoner player, List<MatchReference> lastTenMatches);

	/**
	 * @param id TODO
	 * @see ILeagueDAO#findByUserId(String)
	 */
	Optional<LeagueData> findById(int id);

	/**
	 * @see ILeagueDAO#list()
	 */
	List<LeagueData> list();

	/**
	 * @see ILeagueDAO#deleteById(String)
	 */
	void deleteById(int id);


}
