package org.github.boziroland.DAL.impl;

import org.github.boziroland.DAL.ICommentDAO;
import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.User;

import java.util.*;

public class CommentInMemory implements ICommentDAO {

	private final Map<Integer, Comment> idCommentMap = new HashMap<>();

	@Override
	public void create(Comment comment) {
		idCommentMap.put(comment.getId(), comment);
	}

	@Override
	public Optional<Comment> findById(String id) {
		return Optional.ofNullable(idCommentMap.get(id));
	}

	@Override
	public List<Comment> findByUser(User user) {
		List<Comment> ret = new ArrayList<>();

		for (var c : idCommentMap.entrySet())
			if (c.getValue().getSenderId().equals(user.getId()))
				ret.add(c.getValue());

		return ret;
	}

	@Override
	public List<Comment> list() {
		return new ArrayList<>(idCommentMap.values());
	}

	@Override
	public void deleteById(String id) {
		idCommentMap.remove(id);
	}

	@Override
	public void deleteByUser(User user) {

		for (var c : idCommentMap.entrySet())
			if (c.getValue().getSenderId().equals(user.getId()))
				idCommentMap.remove(c.getKey());
	}

	@Override
	public void delete(int id) {
		idCommentMap.remove(id);
	}
}
