package com.cyhee.rabit.web.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.report.Report;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.cmm.ResponseHelper;
import com.cyhee.rabit.service.report.ReportService;
import com.cyhee.rabit.service.user.UserService;

@RestController
@RequestMapping("rest/v1/reports")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<Page<Report>> getReports(@PageableDefault(sort={"createDate"},direction=Direction.DESC) Pageable pageable) {
		return ResponseEntity.ok(reportService.getReports(pageable));
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Report> getReport(@PathVariable long id) {
		return ResponseEntity.ok(reportService.getReport(id));
	}
	
	@PostMapping
	public ResponseEntity<Void> addReport(@RequestBody Report report) {
		User reporter = userService.getUserByUsername(AuthHelper.getUsername());
		report.setReporter(reporter);
		return ResponseHelper.createdEntity(ContentType.REPORT, report.getId());
	}
}
