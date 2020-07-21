package org.github.boziroland.services;

import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.User;
import org.github.boziroland.DAL.ICommentDAO;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The interface ICommentService defines the performable CRUD operations on the Comment class.
 */
public interface ICommentService {

    void create(Comment comment);

    /**
     * Creates a Comment instance and passes it to
     * @see ICommentDAO#create(Comment)
     *@param sender The User who wrote the comment
     * @param receiver The User whose comment section this comment was sent to
     * @param message The text of the message
     * @param ID The message's id
     * @param time The time at which the comment was sent
     */
    void create(User sender, User receiver, String message, int ID, LocalDateTime time);

    /**
     * @see ICommentDAO#findById(String)
     */
    Optional<Comment> findById(String id);

    /**
     * @see ICommentDAO#findByUser(User)
     */
    List<Comment> findByUser(User user);

    /**
     * @see ICommentDAO#list()
     */
    List<Comment> list();

    /**
     * @see ICommentDAO#deleteById(String)
     */
    void deleteById(String id);

    /**
     * @see ICommentDAO#deleteByUser(User)
     */
    void deleteByUser(User user);

    /**
     * Creates a Comment instance and passes it to
     * @see ICommentDAO#delete(int)
     *@param ID The message's id
     */
    void delete(int ID);

    /**
     * Sends a comment
     * @param from The sender of the comment
     * @param to The receiver of the comment
     * @param message The message of the comment
     */
    void sendComment(User from, User to, String message);

}
