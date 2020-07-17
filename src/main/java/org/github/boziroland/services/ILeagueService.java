package org.github.boziroland.services;

import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import org.github.boziroland.entities.LeagueDataSource;
import org.github.boziroland.DAL.ILeagueDAO;

import java.util.List;
import java.util.Optional;

/**
 * The interface ILeagueService defines the performable CRUD operations on the LeaguePlayer class.
 */
public interface ILeagueService extends APIService {

    /**
     * Creates a LeaguePlayer and passes it to
     * @see ILeagueDAO#createOrUpdate(LeagueDataSource)
     *
     * @param player The League account of the player
     * @param lastTenMatches The last ten matches of the player
     */
    void createOrUpdate(Summoner player, List <MatchReference> lastTenMatches);

    /**
     * @see ILeagueDAO#findByUsername(String)
     */
    List<LeagueDataSource> findByUsername(String name);

    /**
     * @see ILeagueDAO#findByUserId(String)
     */
    Optional<LeagueDataSource> findByuserID(String id);

    /**
     * @see ILeagueDAO#list()
     */
    List<LeagueDataSource> list();

    /**
     * @see ILeagueDAO#deleteByName(String)
     */
    void deleteByName(String name);

    /**
     * @see ILeagueDAO#deleteById(String)
     */
    void deleteById(String id);



}
