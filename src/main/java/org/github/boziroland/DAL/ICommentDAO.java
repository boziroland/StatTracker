package org.github.boziroland.DAL;

import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.User;

import java.util.List;
import java.util.Optional;

/**
 * The interface ICommentDAO defines the performable CRUD operations on the Comment class.
 */
public interface ICommentDAO {

	/**
	 * Creates a comment.
	 *
	 * @param comment The comment to add
	 */
	void create(Comment comment);

	/**
	 * Finds a comment by their ID.
	 *
	 * @param id The ID of the comment we want to find
	 * @return The comment we found, wrapped into an Optional container
	 */
	Optional<Comment> findById(String id);

	/**
	 * Finds a user's comments.
	 *
	 * @param user The user whose comments we want to find
	 * @return The List of comments we found
	 */
	List<Comment> findByUser(User user);

	/**
	 * Lists every comment.
	 *
	 * @return A list of every comment
	 */
	List<Comment> list();

	/**
	 * Deletes a comment by its id.
	 *
	 * @param id The id of the comment we want to remove
	 */
	void deleteById(String id);

	/**
	 * Deletes a user's comments.
	 *
	 * @param user The user whose comments we want to delete
	 */
	void deleteByUser(User user);

	/**
	 * Deletes a comment.
	 *
	 * @param id The id of the comment to delete
	 */
	void delete(int id);
}
