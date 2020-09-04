package org.github.boziroland.repositories;

import org.github.boziroland.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

	User findByName(String name);

	User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.name LIKE %:nameToFind%")
	List<User> findByNameContaining(String nameToFind);
}
