package org.github.boziroland.repositories.apiEntityRepositories;

import org.github.boziroland.entities.apiEntities.OWPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOWPlayerRepository extends JpaRepository<OWPlayer, Integer> {
}
