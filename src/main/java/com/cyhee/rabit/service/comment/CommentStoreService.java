package com.cyhee.rabit.service.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.like.LikeService;

@Service
public class CommentStoreService {

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    public void deleteComment(Long id) {
        Comment comment = commentService.deleteComment(id);
        deleteAllCommentStore(comment);
    }

    public Page<Like> getLikes(Comment goal, Pageable pageable) {
        return likeService.getLikes(ContentType.COMMENT, goal.getId(), pageable);
    }

    public List<Like> getLikes(Comment goal) {
        return likeService.getLikes(ContentType.COMMENT, goal.getId());
    }
	
	public void addLike(Comment comment, User liker) {
		// TODO specific exception
		if(comment.getAuthor().equals(liker))
			throw new RuntimeException("Self like is not allowed");
		Like like = new Like().setAuthor(liker).setType(ContentType.COMMENT).setParentId(comment.getId());
		likeService.addLike(like);
	}

    public void deleteAllCommentStore(Comment comment) {
        for (Like like : getLikes(comment)) {
            likeService.deleteLike(like.getId());
        }
    }
}
