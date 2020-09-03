package org.github.boziroland.entities.apiEntities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableLong;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
public class MyMatchReference {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ID_SEQUENCE")
	@SequenceGenerator(name="ID_SEQUENCE", sequenceName="ID_SEQUENCE", allocationSize=1)
	private Integer id;

	private Long gameId;
	private Integer champion;
	private String lane;
	private String platformId;
	private Integer queue;
	private Integer season;

	@Column(name = "gameDate")
	private Long timestamp;

	public MyMatchReference(MatchReference m) {
		gameId = m.getGameId();
		champion = m.getChampion();
		lane = m.getLane();
		platformId = m.getPlatformId();
		queue = m.getQueue();
		season = m.getSeason();
		timestamp = m.getTimestamp();
	}

}
