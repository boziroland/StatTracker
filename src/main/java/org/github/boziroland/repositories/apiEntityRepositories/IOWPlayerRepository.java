package org.github.boziroland.repositories.apiEntityRepositories;

import org.github.boziroland.entities.apiEntities.OWPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOWPlayerRepository extends JpaRepository<OWPlayer, Integer> {
}
