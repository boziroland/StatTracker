package org.github.boziroland.DAL;

import org.github.boziroland.entities.CommentSection;
import org.github.boziroland.entities.User;

import java.util.Optional;

/**
 * The interface ICommentSectionDAO defines the performable CRUD operations on the CommentSection class.
 */
public interface ICommentSectionDAO {

    /**
     * Creates or updates a CommentSection.
     * @param commentSection the CommentSectionto update/delete
     */
    void createOrUpdate(CommentSection commentSection);

    /**
     * Finds a CommentSection by its id.
     * @param id The id of the CommentSection
     * @return The CommentSection we found, wrapped into an Optional container
     */
    Optional<CommentSection> findById(String id);

    /**
     * Finds a CommentSection by the User it belongs to.
     * @param user The User whose CommentSectionwe want to find
     * @return The CommentSection we found, wrapped into an Optional container
     */
    Optional<CommentSection> findByUser(User user);
}
