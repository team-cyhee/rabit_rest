
package com.cyhee.rabit.service.like;

import com.cyhee.rabit.dao.like.LikeRepository;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.cmm.RadioStatus;
import com.cyhee.rabit.model.like.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("likeService")
public class LikeService {
	@Autowired
	private LikeRepository repository;
	
	public Page<Like> getLikes(ContentType type, Long parentId, Pageable pageable) {
		return repository.findByTypeAndParentIdAndStatusNot(type, parentId, RadioStatus.INACTIVE, pageable);
	}

	public List<Like> getLikes(ContentType type, Long parentId) {
		return repository.findByTypeAndParentIdAndStatusNot(type, parentId, RadioStatus.INACTIVE);
	}

	public Page<Like> getLikes(Pageable pageable) {
		return repository.findByStatusNot(RadioStatus.INACTIVE, pageable);
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
