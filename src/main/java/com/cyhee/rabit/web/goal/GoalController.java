package com.cyhee.rabit.web.goal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

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
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.goal.GoalDTO;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.cmm.ResponseHelper;
import com.cyhee.rabit.service.file.FileService;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.goal.GoalStoreService;
import com.cyhee.rabit.service.user.UserService;

@RestController
@RequestMapping("rest/v1/goals")
public class GoalController {
    @Resource(name="userService")
    private UserService userService;

	@Resource(name="goalService")
	private GoalService goalService;

	@Resource(name="goalStoreService")
    private GoalStoreService goalStoreService;

	@Resource(name="fileService")
    private FileService fileService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<Goal>> getGoals(@PageableDefault(sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(goalService.getGoals(pageable), HttpStatus.OK);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<Goal>> getGoalsByUser() {
        User author = userService.getUserByUsername(AuthHelper.getUsername());
        return new ResponseEntity<>(goalService.getGoalsByAuthor(author), HttpStatus.OK);
    }

	@RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> addGoal(@RequestBody GoalDTO.PostOneFile dto) {
        User author = userService.getUserByUsername(AuthHelper.getUsername());
        Goal goal = new Goal();
        goal.setAuthor(author);
        goal.setContent(dto.getContent());
        goal.setDoUnit(dto.getDoUnit());
        goal.setDoTimes(dto.getDoTimes());
        goal.setStartDate(dto.getStartDate());
        goal.setEndDate(dto.getEndDate());
        if (dto.getFileId() != null) goal.getFiles().add(fileService.getFile(dto.getFileId()));

        goalService.addGoal(goal);
        return ResponseHelper.createdEntity(ContentType.GOAL, goal.getId());
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Goal> getGoal(@PathVariable long id) {
    	return new ResponseEntity<>(goalService.getGoal(id), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> updateGoal(@PathVariable long id, @RequestBody GoalDTO.PostOneFile dto) {
        Goal goal = new Goal();
        goal.setContent(dto.getContent());
        goal.setDoUnit(dto.getDoUnit());
        goal.setDoTimes(dto.getDoTimes());
        goal.setStartDate(dto.getStartDate());
        goal.setEndDate(dto.getEndDate());
        if (dto.getFileId() != null) goal.setFiles(new ArrayList<>(Arrays.asList(fileService.getFile(dto.getFileId()))));

        goalService.updateGoal(id, goal);
        return new ResponseEntity<>(HttpStatus.OK); 
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteGoal(@PathVariable long id) {
    	goalStoreService.deleteGoal(id);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
    
    @GetMapping(value = "/search")
	public ResponseEntity<Page<Goal>> search(@RequestParam String keyword, @PageableDefault Pageable pageable) {
		return new ResponseEntity<Page<Goal>>(goalService.search(keyword, ContentStatus.visible(), pageable), HttpStatus.OK);
	}
}
