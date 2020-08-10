package org.github.boziroland.services.impl;

import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.User;
import org.github.boziroland.repositories.ICommentRepository;
import org.github.boziroland.services.ICommentService;
import org.github.boziroland.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CommentService implements ICommentService {

	@Autowired
	private ICommentRepository commentRepository;

	@Autowired
	private IUserService userService;

	public CommentService() {
	}

	@Override
	public Comment create(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	public Comment create(User sender, User receiver, String message, int ID, LocalDateTime time) {
		return create(new Comment(sender.getId(), receiver.getId(), message, time));
	}

	@Override
	public Optional<Comment> findById(int id) {
		return commentRepository.findById(id);
	}

	@Override
	public List<Comment> findByUser(User user) {
		return commentRepository.findBySenderId(user.getId());
	}

	@Override
	public List<Comment> findByUserId(int userId) {
		Optional<User> user = userService.findById(userId);

		if (user.isPresent())
			return findByUser(user.get());

		return List.of();
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
		commentRepository.deleteBySenderId(user.getId());
	}

	@Override
	public void delete(Comment comment) {
		commentRepository.delete(comment);
	}

	@Override
	public Comment sendComment(User from, User to, String message) {
		Comment comment = new Comment(from.getId(), to.getId(), message, LocalDateTime.now());
		Comment sentComment = create(comment);
		from.getCommentsSent().add(sentComment);
		to.getCommentsOnProfile().add(sentComment);
		userService.update(from);
		userService.update(to);
		return sentComment;
	}

	@Override
	public Comment sendComment(Integer fromId, Integer toId, String message) {
		var from = userService.findById(fromId);
		var to = userService.findById(toId);
		if(from.isPresent() && to.isPresent()){
			return sendComment(from.get(), to.get(), message);
		}
		return null;
	}


}
