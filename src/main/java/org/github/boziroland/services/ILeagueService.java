package org.github.boziroland.services;

import org.github.boziroland.entities.LeaguePlayer;
import org.github.boziroland.DAL.ILeagueDAO;

import java.util.List;

/**
 * The interface ILeagueService defines the performable CRUD operations on the LeaguePlayer class.
 */
public interface ILeagueService {

    /**
     * @see ILeagueDAO#createOrUpdate(LeaguePlayer) 
     */
    void createOrUpdate(LeaguePlayer player);

    /**
     * @see ILeagueDAO#findByuserName(String) 
     */
    List<LeaguePlayer> findByusername(String name);

    /**
     * @see ILeagueDAO#findByUserId(String) 
     */
    List<LeaguePlayer> findByuserID(String id);

    /**
     * @see ILeagueDAO#list() 
     */
    List<LeaguePlayer> list();

    /**
     * @see ILeagueDAO#deleteByName(String) 
     */
    void deleteByName(String name);

    /**
     * @see ILeagueDAO#deleteById(String) 
     */
    void deleteById(String id);

}
