package com.cyhee.rabit.dao.alarm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.cyhee.rabit.model.alarm.Alarm;
import com.cyhee.rabit.model.user.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called AlarmRepository
// CRUD refers Create, Read, Update, Delete

public interface AlarmRepository extends PagingAndSortingRepository<Alarm, Long>, AlarmRepositoryCustom {

    @Query("From Alarm a Where :user = a.author Or :user = a.owner")
    Page<Alarm> findByOwnerOrAuthor(@Param("user")User user, Pageable pageable);
}