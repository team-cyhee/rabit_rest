package com.cyhee.rabit.web.page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.model.follow.Follow;
import com.cyhee.rabit.model.page.MainInfo;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.page.MainInfoService;
import com.cyhee.rabit.service.user.UserService;
import com.cyhee.rabit.service.user.UserStoreService;

@RestController
@RequestMapping("rest/v1/maininfos")
public class MainInfoController {
    @Resource(name="userService")
    private UserService userService;

    @Resource(name= "mainInfoService")
    private MainInfoService mainInfoService;
    
    @Autowired
    private UserStoreService userStoreService;

    /*@RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<MainInfo>> getMainInfos(@PageableDefault(sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
        List<MainInfo> mainInfoList = mainInfoService.getMainInfos(pageable);
        return new ResponseEntity<>(mainInfoList, HttpStatus.OK);
    }*/
    
    @GetMapping
    public ResponseEntity<List<MainInfo>> getMainInfos(Long order) {
    	User user = userService.getUserByUsername(AuthHelper.getUsername());
    	List<Follow> follows = userStoreService.getFollowees(user);
    	List<Long> userIds = follows.stream()
    			.map(follow -> follow.getFollowee().getId())
    			.collect(Collectors.toCollection(ArrayList::new));
    	userIds.add(user.getId());
    	
    	// 30 days
    	long limit = 1000L * 60L * 60L * 24L * 30L;
    	Date from = new Date(System.currentTimeMillis() - limit);
    	
        List<MainInfo> mainInfoList = mainInfoService.getMainInfos(userIds, from, order);
        return new ResponseEntity<>(mainInfoList, HttpStatus.OK);
    }

    @RequestMapping(value="/{username}", method=RequestMethod.GET)
    public ResponseEntity<List<MainInfo>> getUserMainInfos(@PathVariable String username, @PageableDefault(sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
        User author = userService.getUserByUsername(username);

        List<MainInfo> mainInfoList = mainInfoService.getUserMainInfos(author, pageable);
        return new ResponseEntity<>(mainInfoList, HttpStatus.OK);
    }
    
}


