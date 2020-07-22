package org.github.boziroland.repositories;

import org.github.boziroland.entities.LeagueData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILeagueRepository extends JpaRepository<LeagueData, Integer> {
}
