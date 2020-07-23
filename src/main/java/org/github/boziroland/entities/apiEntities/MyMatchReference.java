package org.github.boziroland.entities.apiEntities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@RequiredArgsConstructor
public class MyMatchReference {

    @Id
    @GeneratedValue
    private Integer id;

    private long gameId;
    private int champion;
    private String lane;
    private String platformId;
    private int queue;
    private int season;
    private long timestamp;

    public MyMatchReference(MatchReference m){
        gameId = m.getGameId();
        champion = m.getChampion();
        lane = m.getLane();
        platformId = m.getPlatformId();
        queue = m.getQueue();
        season = m.getSeason();
        timestamp = m.getTimestamp();
    }

}
