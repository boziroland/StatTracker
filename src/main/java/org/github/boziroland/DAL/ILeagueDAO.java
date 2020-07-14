package org.github.boziroland.DAL;

import org.github.boziroland.entities.LeaguePlayer;

import java.util.List;
import java.util.Optional;

public interface ILeagueDAO {

    void createOrUpdate(LeaguePlayer param);
    List<LeaguePlayer> findByuserName(String name);
    Optional<LeaguePlayer> findByUserId(String id);
    List<LeaguePlayer> list();
    void deleteByName(String name);
    void deleteById(String id);
}
