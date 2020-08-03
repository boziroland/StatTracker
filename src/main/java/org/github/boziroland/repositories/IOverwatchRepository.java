package org.github.boziroland.repositories;

import org.github.boziroland.entities.OverwatchData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOverwatchRepository extends JpaRepository<OverwatchData, Integer> {

}
