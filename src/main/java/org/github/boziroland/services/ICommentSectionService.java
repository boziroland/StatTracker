package org.github.boziroland.services;

import org.github.boziroland.entities.CommentSection;
import org.github.boziroland.entities.User;
import org.github.boziroland.DAL.ICommentSectionDAO;

import java.util.Optional;

/**
 * The interface ICommentSectionService defines the performable CRUD operations on the CommentSection class.
 */
public interface ICommentSectionService {

    /**
     * @see ICommentSectionDAO#createOrUpdate(CommentSection)
     */
    void createOrUpdate(CommentSection commentSection);

    /**
     * @see ICommentSectionDAO#findById(String)
     */
    Optional<CommentSection> findById(String id);

    /**
     * @see ICommentSectionDAO#findByUser(User)
     */
    Optional<CommentSection> findByUser(User user);
}
