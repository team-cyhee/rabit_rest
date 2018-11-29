package com.cyhee.rabit.dao.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.report.Report;
import com.cyhee.rabit.model.user.User;

public interface ReportRepository extends JpaRepository<Report, Long> {
	
	@Query("SELECT count(r) FROM Report r WHERE r.targetType = :type and r.targetId = :id")
	int count(@Param("type") ContentType type, @Param("id") Long id);
	
	@Query("SELECT count(r) > 0 FROM Report r WHERE r.targetType = :type and r.targetId = :id and r.reporter = :reporter")
	boolean exists(@Param("type") ContentType type, @Param("id") Long id, @Param("reporter") User reporter);
}
