package org.github.boziroland.repositories.apiEntityRepositories;

import org.github.boziroland.entities.apiEntities.MyMatchReference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMyMatchReferenceRepository extends JpaRepository<MyMatchReference, Integer> {
}
