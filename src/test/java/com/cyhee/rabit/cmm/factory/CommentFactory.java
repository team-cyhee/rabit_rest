package com.cyhee.rabit.cmm.factory;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.comment.Comment;
import com.cyhee.rabit.model.user.User;

public class CommentFactory {
	public static Comment base(User author, ContentType type, Long parentId, String content) {
		Comment comment = new Comment();
		comment.setAuthor(author);
		comment.setType(type);
		comment.setParentId(parentId);
		comment.setContent(content);
		return comment;
	}
}
