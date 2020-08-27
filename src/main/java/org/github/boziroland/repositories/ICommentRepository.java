package org.github.boziroland.repositories;

import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Integer> {

	List<Comment> findBySenderId(Integer senderId);

	List<Comment> findByReceiverId(Integer receiverId);

	void deleteBySenderId(Integer senderID);

}
