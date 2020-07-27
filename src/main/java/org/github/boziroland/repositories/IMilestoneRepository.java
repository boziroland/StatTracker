package org.github.boziroland.repositories;

import org.github.boziroland.entities.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMilestoneRepository extends JpaRepository<Milestone, String> {


}
