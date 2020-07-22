package org.github.boziroland.entities.apiEntities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@RequiredArgsConstructor
public class MySummoner {

    @Id
    String accountId;
    String name;
    String id;
    int profileIconid;
    String puuid;
    long revisionDate;
    int summonerLevel;

    public MySummoner(Summoner s){
        accountId = s.getAccountId();
        name = s.getName();
        id = s.getId();
        profileIconid = s.getProfileIconId();
        puuid = s.getPuuid();
        revisionDate = s.getRevisionDate();
        summonerLevel = s.getSummonerLevel();
    }
}
