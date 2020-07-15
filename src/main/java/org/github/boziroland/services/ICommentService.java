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
     * @see ICommentDAO#create(Comment)
     */
    void create(Comment comment);

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
     * @see ICommentDAO#delete(Comment)
     */
    void delete(Comment comment);

}
