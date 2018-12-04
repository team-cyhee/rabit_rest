
package com.cyhee.rabit.web.goallog;

import javax.annotation.Resource;

import com.cyhee.rabit.model.goallog.GoalLogDTO;
import com.cyhee.rabit.service.file.FileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.goallog.GoalLog;
import com.cyhee.rabit.service.cmm.ResponseHelper;
import com.cyhee.rabit.service.goallog.GoalLogService;
import com.cyhee.rabit.service.goallog.GoalLogStoreService;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("rest/v1/goallogs")
public class GoalLogController {
    @Resource(name="goalLogService")
    private GoalLogService goalLogService;

    @Resource(name="goalLogStoreService")
    private GoalLogStoreService goallogStoreService;

    @Resource(name="fileService")
    private FileService fileService;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<GoalLog>> getGoalLogs(@PageableDefault(sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
        Page<GoalLog> logPage = goalLogService.getGoalLogs(pageable);
        return new ResponseEntity<>(logPage, HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> addGoalLog(@RequestBody GoalLog goallog) {
        goalLogService.addGoalLog(goallog);
        return ResponseHelper.createdEntity(ContentType.GOALLOG, goallog.getId());
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<GoalLog> getGoalLog(@PathVariable long id) {
        return new ResponseEntity<>(goalLogService.getGoalLog(id), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> updateGoalLog(@PathVariable long id, @RequestBody GoalLogDTO.PostOneFile dto) {
        GoalLog goalLog = goalLogService.getGoalLog(id);
        goalLog.setContent(dto.getContent());
        if (dto.getFileId() != null) goalLog.setFiles(new ArrayList<>(Arrays.asList(fileService.getFile(dto.getFileId()))));

        goalLogService.updateGoalLog(id, goalLog);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteGoalLog(@PathVariable long id) {
        goallogStoreService.deleteGoalLog(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    
    @GetMapping(value = "/search")
	public ResponseEntity<Page<GoalLog>> search(@RequestParam String keyword, @PageableDefault Pageable pageable) {
		return new ResponseEntity<Page<GoalLog>>(goalLogService.search(keyword, ContentStatus.visible(), pageable), HttpStatus.OK);
	}
}
