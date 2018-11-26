
package com.cyhee.rabit.web.like;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.like.Like;
import com.cyhee.rabit.service.cmm.ResponseHelper;
import com.cyhee.rabit.service.like.LikeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("rest/v1/likes")
public class LikeController {
	@Resource(name="likeService")
	private LikeService likeService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<Like>> getLikes(@PageableDefault(sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
		Page<Like> likePage = likeService.getLikes(pageable);
        return new ResponseEntity<>(likePage, HttpStatus.OK);
    }
	
	@RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> addLike(@RequestBody Like like) {
        likeService.addLike(like);
        return ResponseHelper.createdEntity(ContentType.LIKE, like.getId());
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Like> getLike(@PathVariable long id) {
    	return new ResponseEntity<>(likeService.getLike(id), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> updateLike(@PathVariable long id, @RequestBody Like likeForm) {
        likeService.updateLike(id, likeForm);
        return new ResponseEntity<>(HttpStatus.OK); 
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteLike(@PathVariable long id) {
        likeService.deleteLike(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
