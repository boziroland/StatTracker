package org.github.boziroland.services.impl;

import org.github.boziroland.DAL.ICommentDAO;
import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.User;
import org.github.boziroland.services.ICommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CommentService implements ICommentService {

    ICommentDAO dao;
    private int id = 0;

    public CommentService(ICommentDAO dao) {
        this.dao = dao;
    }

    @Override
    public void create(Comment comment) {
        dao.create(comment);
    }

    @Override
    public void create(User sender, User receiver, String message, int ID, LocalDateTime time) {
        create(new Comment(ID, sender, receiver, message, time));
    }

    @Override
    public Optional<Comment> findById(String id) {
        return dao.findById(id);
    }

    @Override
    public List<Comment> findByUser(User user) {
        return dao.findByUser(user);
    }

    @Override
    public List<Comment> list() {
        return dao.list();
    }

    @Override
    public void deleteById(String id) {
        dao.deleteById(id);
    }

    @Override
    public void deleteByUser(User user) {
        dao.deleteByUser(user);
    }

    @Override
    public void delete(int ID) {
        dao.delete(ID);
    }

    @Override
    public void sendComment(User from, User to, String message) {
        Comment comment = new Comment(id++, from, to, message, LocalDateTime.now());
        from.getCommentsSent().add(comment);
        to.getCommentsOnProfile().add(comment);
    }

}
