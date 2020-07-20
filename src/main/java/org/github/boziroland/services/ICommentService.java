package org.github.boziroland.services;

import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.User;
import org.github.boziroland.DAL.ICommentDAO;


import java.util.List;
import java.util.Optional;

/**
 * The interface ICommentService defines the performable CRUD operations on the Comment class.
 */
public interface ICommentService {

    /**
     * Creates a Comment instance and passes it to
     * @see ICommentDAO#create(Comment)
     *
     * @param sender The user who wrote the comment
     * @param message The text of the message
     * @param ID The message's id
     */
    void create(User sender, String message, String ID);

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
     * @see ICommentDAO#delete(Comment)
     *
     * @param sender The user who wrote the comment
     * @param message The text of the message
     * @param ID The message's id
     */
    void delete(User sender, String message, String ID);

    /**
     * TODO
     * @param user
     */
    void getProfileComments(User user);

}
