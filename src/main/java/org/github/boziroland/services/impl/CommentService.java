package org.github.boziroland.services.impl;

import org.github.boziroland.DAL.ICommentDAO;
import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.User;
import org.github.boziroland.services.ICommentService;

import java.util.List;
import java.util.Optional;

public class CommentService implements ICommentService {

    ICommentDAO dao;
    private int id = 0;

    public CommentService(ICommentDAO dao) {
        this.dao = dao;
    }

    @Override
    public void create(User sender, User receiver, String message, int ID) {
        dao.create(new Comment(sender, receiver, message, ID));
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
    public void delete(User sender, User receiver, String message, int ID) {
        dao.delete(new Comment(sender, receiver, message, ID));
    }

    @Override
    public void getProfileComments(User user) {
        //TODO
    }

    @Override
    public void sendComment(User from, User to, String message) {
        Comment comment = new Comment(from, to, message, id++);
        //to.getComm
    }

}
