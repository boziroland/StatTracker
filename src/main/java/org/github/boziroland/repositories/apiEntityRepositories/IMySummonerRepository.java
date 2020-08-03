package org.github.boziroland.repositories.apiEntityRepositories;

import org.github.boziroland.entities.apiEntities.MySummoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMySummonerRepository extends JpaRepository<MySummoner, Integer> {
}
