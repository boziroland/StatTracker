package org.github.boziroland.services.impl;

import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.User;
import org.github.boziroland.repositories.ICommentRepository;
import org.github.boziroland.services.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CommentService implements ICommentService {

    @Autowired
    ICommentRepository commentRepository;

    public CommentService() {}

    @Override
    public void create(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void create(User sender, User receiver, String message, int ID, LocalDateTime time) {
        create(new Comment(ID, sender, receiver, message, time));
    }

    @Override
    public Optional<Comment> findById(int id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findByUser(User user) {
        return commentRepository.findBySender(user);
    }

    @Override
    public List<Comment> list() {
        return commentRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void deleteByUser(User user) {
        commentRepository.deleteBySender(user);
    }

    @Override
    public void delete(int ID) {
        commentRepository.deleteById(ID);
    }

    @Override
    public void sendComment(User from, User to, String message) {
        Comment comment = new Comment(-1, from, to, message, LocalDateTime.now());
        from.getCommentsSent().add(comment);
        to.getCommentsOnProfile().add(comment);
    }

}
