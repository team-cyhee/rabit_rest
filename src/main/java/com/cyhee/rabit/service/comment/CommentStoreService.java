package com.cyhee.rabit.service.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.cmm.BaseEntity;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.like.LikeService;

@Service
public class CommentStoreService {

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    public void deleteComment(Long id) {    	
        Comment comment = commentService.getComment(id);
        
        AuthHelper.isAuthorOrAdmin(comment);
        
        commentService.delete(comment);
        deleteAllCommentStore(comment);
    }
	
	public void deleteByParent(BaseEntity parent) {
		List<Comment> list = commentService.getComments(ContentType.findByKey(parent.getClass()), parent.getId());
		
		for (Comment comment : list) {
			commentService.delete(comment);
			deleteAllCommentStore(comment);
		}
	}

    public Page<Like> getLikes(Comment goal, Pageable pageable) {
        return likeService.getLikes(ContentType.COMMENT, goal.getId(), pageable);
    }

    public List<Like> getLikes(Comment goal) {
        return likeService.getLikes(ContentType.COMMENT, goal.getId());
    }
	
	public void addLike(Comment comment, User liker) {
		// TODO specific exception
		/*if(comment.getAuthor().equals(liker))
			throw new RuntimeException("Self like is not allowed");*/
		Like like = new Like().setAuthor(liker).setType(ContentType.COMMENT).setParentId(comment.getId());
		likeService.addLike(like);
	}

    private void deleteAllCommentStore(Comment comment) {
        likeService.deleteByParent(comment);
    }
}
