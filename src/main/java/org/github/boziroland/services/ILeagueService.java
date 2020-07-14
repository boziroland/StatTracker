package org.github.boziroland.services;

import org.github.boziroland.entities.LeaguePlayer;

import java.util.List;

public interface ILeagueService {

    void createOrUpdate(LeaguePlayer param);
    List<LeaguePlayer> findByusername();
    List<LeaguePlayer> findByuserID();
    List<LeaguePlayer> list();
    void deleteByName(String name);
    void deleteById(String id);

}
