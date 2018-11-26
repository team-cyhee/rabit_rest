package com.cyhee.rabit.web.cs;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.cs.Question;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.cmm.ResponseHelper;
import com.cyhee.rabit.service.cs.QuestionService;
import com.cyhee.rabit.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("rest/v1/questions")
public class QuestionController {
    @Resource(name="userService")
    private UserService userService;

    @Resource(name="questionService")
	private QuestionService questionService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<Question>> getQuestions(@PageableDefault(sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(questionService.getQuestions(pageable), HttpStatus.OK);
    }

	@RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> addQuestion(@RequestBody Question question) {
        User author = userService.getUserByUsername(AuthHelper.getUsername());
        question.setAuthor(author);
        questionService.addQuestion(author, author, null, question);
        return ResponseHelper.createdEntity(ContentType.QUESTION, question.getId());
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Question> getQuestion(@PathVariable long id) {
    	return new ResponseEntity<>(questionService.getQuestion(id), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteQuestion(@PathVariable long id, @PageableDefault(sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
        questionService.deleteQuestion(id);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}
