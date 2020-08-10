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

	private MutableLong gameId;
	private MutableInt champion;
	private String lane;
	private String platformId;
	private MutableInt queue;
	private MutableInt season;

	@Column(name = "gameDate")
	private MutableLong timestamp;

	public MyMatchReference(MatchReference m) {
		gameId = new MutableLong(m.getGameId());
		champion = new MutableInt(m.getChampion());
		lane = m.getLane();
		platformId = m.getPlatformId();
		queue = new MutableInt(m.getQueue());
		season = new MutableInt(m.getSeason());
		timestamp = new MutableLong(m.getTimestamp());
	}

}
