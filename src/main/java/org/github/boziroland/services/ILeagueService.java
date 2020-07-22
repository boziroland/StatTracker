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
     * @see ILeagueDAO#createOrUpdate(LeagueData)
     *
     * @param player The League account of the player
     * @param lastTenMatches The last ten matches of the player
     */
    void createOrUpdate(Summoner player, List <MatchReference> lastTenMatches);

    /**
     * @see ILeagueDAO#findByUserId(String)
     * @param id TODO
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
