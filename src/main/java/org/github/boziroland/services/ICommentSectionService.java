package org.github.boziroland.services;

import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.CommentSection;
import org.github.boziroland.entities.User;
import org.github.boziroland.DAL.ICommentSectionDAO;

import java.util.List;
import java.util.Optional;

/**
 * The interface ICommentSectionService defines the performable CRUD operations on the CommentSection class.
 */
public interface ICommentSectionService {

    /**
     * Creates a CommentSection instance and passes it to
     * @see CommentSection
     *
     * @param user The User whom this comment section belongs to
     * @param messages The messages in the comment section
     */
    void createOrUpdate(User user, List<Comment> messages);

    /**
     * @see ICommentSectionDAO#findByUser(User)
     */
    Optional<CommentSection> findByUser(User user);
}
