package org.github.boziroland.repositories;

import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Integer> {

	List<Comment> findBySender(User sender);

	void deleteBySender(User sender);

}
