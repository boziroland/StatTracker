package org.github.boziroland.services.impl;

import org.github.boziroland.DAL.ICommentSectionDAO;
import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.CommentSection;
import org.github.boziroland.entities.User;
import org.github.boziroland.services.ICommentSectionService;

import java.util.List;
import java.util.Optional;

public class CommentSectionService implements ICommentSectionService {

    ICommentSectionDAO dao;

    public CommentSectionService(ICommentSectionDAO dao) {
        this.dao = dao;
    }

    @Override
    public void createOrUpdate(User user, List<Comment> messages) {
        dao.createOrUpdate(new CommentSection(user, messages));
    }

    @Override
    public Optional<CommentSection> findByUser(User user) {
        return dao.findByUser(user);
    }
}
