package com.cyhee.rabit.dao.cs;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cs.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called QuestionRepository
// CRUD refers Create, Read, Update, Delete

public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
	Page<Question> findAllByStatusIn(List<ContentStatus> statusList, Pageable pageable);
}