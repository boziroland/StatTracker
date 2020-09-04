package org.github.boziroland.services;

import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.User;
import org.github.boziroland.repositories.ICommentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The interface ICommentService defines the performable CRUD operations on the
 *
 * @see Comment class.
 */
public interface ICommentService {

	/**
	 * Saves a comment
	 *
	 * @param comment The comment to be saved
	 * @return The stored comment
	 */
	Comment create(Comment comment);

	/**
	 * Creates a Comment instance and passes it to
	 *
	 * @param sender   The User who wrote the comment
	 * @param receiver The User whose comment section this comment was sent to
	 * @param message  The text of the message
	 * @param ID       The message's id
	 * @param time     The time at which the comment was sent
	 * @return The saved comment
	 * @see ICommentRepository#save(Object)
	 */
	Comment create(User sender, User receiver, String message, int ID, LocalDateTime time);

	/**
	 * Finds a comment by its id
	 *
	 * @param id The id of the comment to be found
	 * @return The comment, wrapped in an optional container
	 */
	Optional<Comment> findById(int id);

	/**
	 * Lists every comment made by the user in the parameter
	 *
	 * @param user The user whose comments we want to find
	 * @return The user's comments
	 */
	List<Comment> findBySender(User user);

	/**
	 * Lists every comment made by the user whose id is in the parameter
	 *
	 * @param userId The id of the user whose comments we want to find
	 * @return The user's comments
	 */
	List<Comment> findBySenderId(int userId);

	/**
	 * Lists every comment made to the user in the parameter
	 *
	 * @param user The user whose received comments we want to find
	 * @return The user's received comments
	 */
	List<Comment> findByReceiver(User user);

	/**
	 * Lists every comment made to the user whose id is in the parameter
	 *
	 * @param userId The id of the user whose received comments we want to find
	 * @return The user's received comments
	 */
	List<Comment> findByReceiverId(int userId);

	/**
	 * Lists every Comment
	 *
	 * @return A list of every Comment
	 */
	List<Comment> list();

	/**
	 * Deletes a comment by its id
	 *
	 * @param id The id of the comment we want to remove
	 */
	void deleteById(int id);

	/**
	 * Deletes every comment made by a user
	 *
	 * @param user The user whose comments we want to remove
	 */
	void deleteByUser(User user);

	/**
	 * Deletes a Comment
	 *
	 * @param comment The commentwe want to remove
	 */
	void delete(Comment comment);

	/**
	 * Sends a comment
	 *
	 * @param from    The sender of the comment
	 * @param to      The receiver of the comment
	 * @param message The message of the comment
	 * @return The saved comment
	 */
	Comment sendComment(User from, User to, String message);

	/**
	 * Sends a comment
	 *
	 * @param fromId  The id of the sender of the comment
	 * @param toId    The id of thereceiver of the comment
	 * @param message The message of the comment
	 * @return The saved comment
	 */
	Comment sendComment(Integer fromId, Integer toId, String message);

}
