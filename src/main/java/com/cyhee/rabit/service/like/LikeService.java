
package com.cyhee.rabit.service.like;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.dao.like.LikeRepository;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.model.cmm.BaseEntity;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.cmm.RadioStatus;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.ContentHelper;

@Service("likeService")
public class LikeService {
	@Autowired
	private LikeRepository repository;
	
	public Page<Like> getLikes(ContentType type, Long parentId, Pageable pageable) {
		return repository.findByTypeAndParentIdAndStatusIn(type, parentId, RadioStatus.visible(), pageable);
	}

	public List<Like> getLikes(ContentType type, Long parentId) {
		return repository.findByTypeAndParentIdAndStatusIn(type, parentId, RadioStatus.visible());
	}

	public Page<Like> getLikes(Pageable pageable) {
		return repository.findByStatusIn(RadioStatus.visible(), pageable);
	}

	public Like getLike(long id) {
		Optional<Like> like = repository.findById(id);
		if(!like.isPresent())
			throw new NoSuchContentException(ContentType.LIKE, id);
		return like.get();
	}

	public void addLike(Like like) {
		repository.save(like);
	}
	
	public void addLike(BaseEntity content, User liker) {
		if(ContentHelper.getAuthor(content).equals(liker))
			throw new RuntimeException();
		ContentType contentType = ContentType.findByKey(content.getClass());
		Like like = new Like().setAuthor(liker).setType(contentType).setParentId(content.getId());
		addLike(like);
	}

	public void updateLike(long id, Like source) {
		Like like = getLike(id);
		setLikeProps(like, source);
		repository.save(like);
	}

	public void deleteLike(long id) {
		Like like = getLike(id);
		like.setStatus(RadioStatus.INACTIVE);
		repository.save(like);
	}
	
	private void setLikeProps(Like target, Like source) {
		target.setStatus(source.getStatus());
	}
}
