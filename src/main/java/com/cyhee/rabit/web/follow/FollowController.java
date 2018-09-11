package com.cyhee.rabit.web.follow;

import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.service.follow.FollowService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("rest/v1/follows")
public class FollowController {
    @Resource(name="basicFollowService")
    private FollowService followService;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<Follow>> getFollow(@PageableDefault Pageable pageable) {
        return new ResponseEntity<>(followService.getFollows(pageable), HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> addFollow(@RequestBody Follow follow) {
        followService.addFollow(follow);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Follow> getFollow(@PathVariable long id) {
        return new ResponseEntity<>(followService.getFollow(id), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> updateFollow(@PathVariable long id, @RequestBody Follow followForm) {
        followService.updateFollow(id, followForm);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteFollow(@PathVariable long id) {
        followService.deleteFollow(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
