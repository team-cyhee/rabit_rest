package com.cyhee.rabit.service.cs;

import com.cyhee.rabit.dao.cs.QuestionRepository;
import com.cyhee.rabit.dao.goal.GoalRepository;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.cs.Question;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.notice.Notice;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("questionService")
public class QuestionService {
	@Autowired
	private QuestionRepository questionRepository;

	public Page<Question> getQuestions(Pageable pageable) {
		return questionRepository.findAll(pageable);
	}

	public Question getQuestion(long id) {
		Optional<Question> question = questionRepository.findById(id);
		if(!question.isPresent())
			throw new NoSuchContentException(ContentType.QUESTION, id);
		return question.get();
	}

	public Page<Question> getQuestionsByStatusIn(List<ContentStatus> statusList, Pageable pageable) {
		return questionRepository.findAllByStatusIn(statusList, pageable);
	}

	public void addQuestion(Question question) {
        questionRepository.save(question);
	}

	public void deleteQuestion(long id) {
		Question question = getQuestion(id);

		AuthHelper.isAuthorOrAdmin(question);

		delete(question);
	}

	void delete(Question question) {
        question.setStatus(ContentStatus.DELETED);
		questionRepository.save(question);
	}
}
