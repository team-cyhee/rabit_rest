package com.cyhee.rabit.web.page;

import com.cyhee.rabit.model.page.MainInfo;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.page.MainInfoService;
import com.cyhee.rabit.service.user.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("rest/v1/maininfos")
public class MainInfoController {
    @Resource(name="userService")
    private UserService userService;

    @Resource(name= "mainInfoService")
    private MainInfoService mainInfoService;

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<MainInfo>> getMainInfos(@PageableDefault(sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
        List<MainInfo> mainInfoList = mainInfoService.getMainInfos(pageable);
        return new ResponseEntity<>(mainInfoList, HttpStatus.OK);
    }

    @RequestMapping(value="/{username}", method=RequestMethod.GET)
    public ResponseEntity<List<MainInfo>> getUserMainInfos(@PathVariable String username, @PageableDefault(sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
        User author = userService.getUserByUsername(username);

        List<MainInfo> mainInfoList = mainInfoService.getUserMainInfos(author, pageable);
        return new ResponseEntity<>(mainInfoList, HttpStatus.OK);
    }
}


