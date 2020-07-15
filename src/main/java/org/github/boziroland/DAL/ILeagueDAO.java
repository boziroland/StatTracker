package org.github.boziroland.DAL;

import org.github.boziroland.entities.LeaguePlayer;

import java.util.List;
import java.util.Optional;

/**
 * The interface ILeagueDAO defines the performable CRUD operations on the LeaguePlayer class.
 */
public interface ILeagueDAO {

    /**
     * Creates or updates an existing record of a LeaguePlayer.
     * @param player The LeaguePlayer we want to add/update
     */
    void createOrUpdate(LeaguePlayer player);

    /**
     * Finds LeaguePlayers by their name.
     * @param name The name of the LeaguePlayers we want to find
     * @return A List of the found LeaguePlayers
     */
    List<LeaguePlayer> findByuserName(String name);

    /**
     * Finds a LeaguePlayer by their AccountId.
     * @param id The AccountId of the LeaguePlayer we want to find
     * @return The LeaguePlayer we found, wrapped into an Optional container
     */
    Optional<LeaguePlayer> findByUserId(String id);

    /**
     * Lists every LeaugePlayer.
     * @return A List of every LeaguePlayer
     */
    List<LeaguePlayer> list();

    /**
     * Removes every LeaguePlayer with a certain name.
     * @param name The LeaguePlayer name we want to remove
     */
    void deleteByName(String name);

    /**
     * Removes a LeaguePlayer by their AccountId.
     * @param id The AccountId of the player we want to remove
     */
    void deleteById(String id);
}