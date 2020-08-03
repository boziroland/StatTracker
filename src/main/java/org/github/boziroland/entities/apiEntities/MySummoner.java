package org.github.boziroland.entities.apiEntities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableLong;

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
	private MutableInt profileIconid;
	private String puuid;
	private MutableLong revisionDate;
	private MutableInt summonerLevel;

	public MySummoner(Summoner s) {
		accountId = s.getAccountId();
		name = s.getName();
		idLeague = s.getId();
		profileIconid = new MutableInt(s.getProfileIconId());
		puuid = s.getPuuid();
		revisionDate = new MutableLong(s.getRevisionDate());
		summonerLevel = new MutableInt(s.getSummonerLevel());
	}
}
