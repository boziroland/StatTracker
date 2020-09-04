package org.github.boziroland.controllers;

import org.github.boziroland.entities.Comment;
import org.github.boziroland.services.ICommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	private ICommentService commentService;

	@GetMapping
	public ResponseEntity<List<Comment>> users() {
		LOGGER.info("GET Request: /comments");
		return ResponseEntity.ok(commentService.list());
	}

	@GetMapping("/{id}")
	public ResponseEntity<List<Comment>> userCommentsById(@PathVariable("id") Integer id) {
		LOGGER.info("GET Request: /comments/{}", id);
		return ResponseEntity.ok(commentService.findBySenderId(id));
	}

	@PostMapping("/post")
	public ResponseEntity<Comment> postComment(@RequestBody Map<String, String> commentData) {

		Integer from = Integer.parseInt(commentData.get("from"));
		Integer to = Integer.parseInt(commentData.get("to"));
		String message = commentData.get("message");

		LOGGER.info("POST Request: /post");
		Comment sentComment = commentService.sendComment(from, to, message);
		if (sentComment != null)
			return ResponseEntity.ok(sentComment);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
}
