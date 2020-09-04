package org.github.boziroland.entities.apiEntities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
public class MySummoner {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQUENCE")
	@SequenceGenerator(name = "ID_SEQUENCE", sequenceName = "ID_SEQUENCE", allocationSize = 1)
	private Integer id;

	private String accountId;
	private String name;
	private String idLeague;
	private Integer profileIconid;
	private String puuid;
	private Long revisionDate;
	private Integer summonerLevel;
	private Integer playedMatches;

	public MySummoner(Summoner s) {
		accountId = s.getAccountId();
		name = s.getName();
		idLeague = s.getId();
		profileIconid = s.getProfileIconId();
		puuid = s.getPuuid();
		revisionDate = s.getRevisionDate();
		summonerLevel = s.getSummonerLevel();
	}
}
