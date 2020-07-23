package org.github.boziroland.entities.apiEntities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@RequiredArgsConstructor
public class MySummoner {

    @Id
    @GeneratedValue
    private Integer id;
    private String accountId;
    private String name;
    private String idLeague;
    private int profileIconid;
    private String puuid;
    private long revisionDate;
    private int summonerLevel;

    public MySummoner(Summoner s){
        accountId = s.getAccountId();
        name = s.getName();
        idLeague = s.getId();
        profileIconid = s.getProfileIconId();
        puuid = s.getPuuid();
        revisionDate = s.getRevisionDate();
        summonerLevel = s.getSummonerLevel();
    }
}
