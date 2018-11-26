
package com.cyhee.rabit.web.comment;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.cmm.ResponseHelper;
import com.cyhee.rabit.service.comment.CommentService;
import com.cyhee.rabit.service.comment.CommentStoreService;
import com.cyhee.rabit.service.user.UserService;

@RestController
@RequestMapping("/rest/v1/comments")
public class CommentController {
	@Resource(name="commentService")
	private CommentService commentService;

	@Resource(name="commentStoreService")
    private CommentStoreService commentStoreService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<Comment>> getComments(@PageableDefault(sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
		Page<Comment> commentPage = commentService.getComments(pageable);
        return new ResponseEntity<Page<Comment>>(commentPage, HttpStatus.OK);
    }
	
/*	@RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> addComment(HttpServletRequest request, @RequestBody Comment comment) {
		User author = userService.getUserByUsername(AuthHelper.getUsername());
		comment.setAuthor(author);
    	commentService.addComment(comment);
    	return ResponseHelper.createdEntity(ContentType.GOAL, comment.getId());
    }*/
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Comment> getComment(@PathVariable long id) {
    	return new ResponseEntity<>(commentService.getComment(id), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> updateComment(@PathVariable long id, @RequestBody Comment commentForm) {
    	commentService.updateComment(id, commentForm);
        return new ResponseEntity<>(HttpStatus.OK); 
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteComment(@PathVariable long id) {
    	commentStoreService.deleteComment(id);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}
