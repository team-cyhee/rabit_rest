package com.cyhee.rabit.web.page;

import com.cyhee.rabit.model.page.GoalInfo;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.page.GoalInfoService;
import com.cyhee.rabit.service.user.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("rest/v1/goals/info")
public class GoalInfoController {
    @Resource(name="userService")
    private UserService userService;

    @Resource(name="goalInfoService")
    private GoalInfoService goalInfoService;

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<GoalInfo> getGoalInfo(@PathVariable long id) {
        return new ResponseEntity<>(goalInfoService.getGoalInfo(id), HttpStatus.OK);
    }

    @RequestMapping(value="/user", method=RequestMethod.GET)
    public ResponseEntity<List<GoalInfo>> getGoalInfoByUser(@PageableDefault(sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
        User author = userService.getUserByUsername(AuthHelper.getUsername());

        return new ResponseEntity<>(goalInfoService.getGoalInfosByUser(author, pageable), HttpStatus.OK);
    }

    @RequestMapping(value="/user/{username}", method=RequestMethod.GET)
    public ResponseEntity<List<GoalInfo>> getGoalInfoByUser(@PathVariable String username, @PageableDefault(sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
        User author = userService.getUserByUsername(username);

        return new ResponseEntity<>(goalInfoService.getGoalInfosByUser(author, pageable), HttpStatus.OK);
    }
}