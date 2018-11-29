package com.cyhee.rabit.service.report;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.dao.report.ReportRepository;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.model.cmm.Content;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.report.Report;
import com.cyhee.rabit.service.cmm.ContentService;

@Service
public class ReportService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	ReportRepository reportRepository;
	
	@Autowired
	ContentService contentService;
 
	@Transactional
	public Report addReport(Report report) {
		// To check target exists, if not it will throw Exception 
		Content content = contentService.getContent(report.getTargetType(), report.getTargetId());
		
		if(reportRepository.exists(report.getTargetType(), report.getTargetId(), report.getReporter()))
			throw new DataIntegrityViolationException("Already reported!");
		reportRepository.save(report);
		
		if(reportRepository.count(report.getTargetType(), report.getTargetId()) >= 5) {
			content.setStatus(ContentStatus.REPORTED);
			entityManager.merge(content);
		}				
		
		return report;
	}
	
	public Report getReport(Long id) {
		Optional<Report> report = reportRepository.findById(id);
		if(!report.isPresent())
			throw new NoSuchContentException(ContentType.REPORT, id);
		return report.get();
	}
	
	public Page<Report> getReports(Pageable pageable) {
		return reportRepository.findAll(pageable);
	}
}
