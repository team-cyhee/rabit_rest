package com.cyhee.rabit.web.page;

import com.cyhee.rabit.model.page.WallInfo;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.page.WallInfoService;
import com.cyhee.rabit.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("rest/v1/wallinfo")
public class WallInfoController {
    @Resource(name="userService")
    private UserService userService;

    @Resource(name="wallInfoService")
    private WallInfoService wallInfoService;

    @RequestMapping(value="/{username}", method=RequestMethod.GET)
    public ResponseEntity<WallInfo> getFollowInfo(@PathVariable String username) {
        User author = userService.getUserByUsername(username);

        return new ResponseEntity<>(wallInfoService.getWallInfos(author), HttpStatus.OK);
    }
}
