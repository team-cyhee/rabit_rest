package com.cyhee.rabit.web.page;

import com.cyhee.rabit.model.page.GoalLogInfo;
import com.cyhee.rabit.service.page.GoalLogInfoService;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("rest/v1/goallogs/info")
public class GoalLogInfoController {
    @Resource(name="goalLogInfoService")
    private GoalLogInfoService goalLogInfoService;

    @GetMapping
    public ResponseEntity<List<GoalLogInfo>> getGoalLogs(@PageableDefault Pageable pageable) {
        List<GoalLogInfo> logPage = goalLogInfoService.getGoalLogInfos(pageable);
        return new ResponseEntity<>(logPage, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<GoalLogInfo> getGoalLogInfo(@PathVariable long id) {
        return new ResponseEntity<>(goalLogInfoService.getGoalLogInfo(id), HttpStatus.OK);
    }
}