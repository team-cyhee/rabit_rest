package com.cyhee.rabit.dao.alarm;

import com.cyhee.rabit.model.alarm.Alarm;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.notice.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called AlarmRepository
// CRUD refers Create, Read, Update, Delete

public interface AlarmRepository extends PagingAndSortingRepository<Alarm, Long> {
}