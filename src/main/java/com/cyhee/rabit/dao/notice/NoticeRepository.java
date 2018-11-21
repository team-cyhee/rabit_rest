package com.cyhee.rabit.dao.notice;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.notice.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called QuestionRepository
// CRUD refers Create, Read, Update, Delete

public interface NoticeRepository extends PagingAndSortingRepository<Notice, Long> {

	@Query("select n.title from Notice n where n.status in :statusList")
	Page<Notice> findAllTitles(@Param("statusList") List<ContentStatus> statusList, Pageable pageable);

	Page<Notice> findAllByStatusIn(List<ContentStatus> statusList, Pageable pageable);
}