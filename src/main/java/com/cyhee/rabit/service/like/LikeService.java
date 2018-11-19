
package com.cyhee.rabit.service.like;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.dao.like.LikeRepository;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.model.cmm.BaseEntity;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.cmm.RadioStatus;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;

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

	public Page<User> getLikers(ContentType type, Long parentId, Pageable pageable) {
		return repository.findLikers(type, parentId, RadioStatus.visible(), pageable);
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
	
	public boolean existsByContentAndAuthor(ContentType type, Long parentId, User author) {
		return repository.existsByContentAndAuthor(type, parentId, author);
	}

	public void addLike(Like like) {
		repository.save(like);
	}
	
	public Like addLike(BaseEntity content, User liker) {
		//if (ContentHelper.getOwner(content).equals(liker))
		//	throw new RuntimeException();
		ContentType contentType = ContentType.findByKey(content.getClass());
		
		if(existsByContentAndAuthor(contentType, content.getId(), liker))
			throw new DataIntegrityViolationException("Already liked content");
		
		Like like = new Like().setAuthor(liker).setType(contentType).setParentId(content.getId());
		addLike(like);
		return like;
	}

	public void updateLike(long id, Like source) {
		Like like = getLike(id);
		
		AuthHelper.isAuthorOrAdmin(like);
		
		setLikeProps(like, source);
		repository.save(like);
	}

	public void deleteLike(long id) {
		Like like = getLike(id);
		
		AuthHelper.isAuthorOrAdmin(like);
		
		delete(like);
	}
	
	public void deleteLike(ContentType type, Long parentId, User liker) {
		Optional<Like> like = repository.findByContentAndAuthor(type, parentId, liker);
		if(!like.isPresent())
			throw new NoSuchContentException(ContentType.LIKE);
		
		AuthHelper.isAuthorOrAdmin(like.get());
		
		delete(like.get());
	}
	
	public void deleteByParent(BaseEntity parent) {
		for(Like like : getLikes(ContentType.findByKey(parent.getClass()), parent.getId()))
			delete(like);
	}
	
	void delete(Like like) {
		like.setStatus(RadioStatus.INACTIVE);
		repository.save(like);
	}
	
	private void setLikeProps(Like target, Like source) {
		target.setStatus(source.getStatus());
	}
}
