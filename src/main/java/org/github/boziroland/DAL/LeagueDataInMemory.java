package org.github.boziroland.DAL;

import org.github.boziroland.entities.LeaguePlayer;

import java.util.*;

public class LeagueDataInMemory implements ILeagueDAO {

    Map<String, LeaguePlayer> idPlayerMap = new HashMap<>();

    @Override
    public void createOrUpdate(LeaguePlayer player) {
        idPlayerMap.put(player.getPlayer().getAccountId(), player);
    }

    @Override
    public List<LeaguePlayer> findByuserName(String name) {
        var ret = new ArrayList<LeaguePlayer>();

        for(var player : idPlayerMap.entrySet()){
            var currentPlayer = player.getValue();
            if(currentPlayer.getPlayer().getName().equals(name))
                ret.add(currentPlayer);
        }

        return ret;
    }

    @Override
    public Optional<LeaguePlayer> findByUserId(String id) {
        return Optional.ofNullable(idPlayerMap.get(id));
    }

    @Override
    public List<LeaguePlayer> list() {
        return new ArrayList<>(idPlayerMap.values());
    }

    @Override
    public void deleteByName(String name) {
        var usersToRemove = findByuserName(name);

        for(var user : usersToRemove){
            idPlayerMap.remove(user.getPlayer().getAccountId());
        }
    }

    @Override
    public void deleteById(String id) {
        idPlayerMap.remove(id);
    }
}
