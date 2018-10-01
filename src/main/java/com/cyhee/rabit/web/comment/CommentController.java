
package com.cyhee.rabit.web.comment;

import javax.annotation.Resource;

import com.cyhee.rabit.service.comment.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.model.comment.Comment;

@RestController
@RequestMapping("rest/v1/comments")
public class CommentController {
	@Resource(name="commentService")
	private CommentService commentService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<Comment>> getComments(@PageableDefault Pageable pageable) {
		Page<Comment> commentPage = commentService.getComments(pageable);
        return new ResponseEntity<Page<Comment>>(commentPage, HttpStatus.OK);
    }
	
	@RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> addComment(@RequestBody Comment comment) {
    	commentService.addComment(comment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
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
    	commentService.deleteComment(id);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}
