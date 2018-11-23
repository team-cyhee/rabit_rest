package com.cyhee.rabit.web.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.comment.CommentService;
import com.cyhee.rabit.service.comment.CommentStoreService;
import com.cyhee.rabit.service.like.LikeService;
import com.cyhee.rabit.service.user.UserService;

@RestController
@RequestMapping("rest/v1/comment/{id}")
public class CommentStoreController {

	@Autowired
	private CommentService commentService;
	@Autowired
	private CommentStoreService commentStoreService;
	@Autowired
	private UserService userService;
	@Autowired
	private LikeService likeService;
	
	@GetMapping("/likes")
	public ResponseEntity<Page<Like>> getLikes(@PathVariable Long id, @PageableDefault Pageable pageable) {
		Comment comment = commentService.getComment(id);
        return new ResponseEntity<>(commentStoreService.getLikes(comment, pageable), HttpStatus.OK);
    }
    
    @PostMapping("/likes")
    public ResponseEntity<Void> getLikes(@PathVariable Long id) {
		Comment comment = commentService.getComment(id);
    	
    	String username = AuthHelper.getUsername();
    	User liker = userService.getUserByUsername(username);
    	
		likeService.addLike(comment.getAuthor(), liker, comment, liker);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
