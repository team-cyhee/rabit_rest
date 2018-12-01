package com.cyhee.rabit.cmm.factory;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.report.Report;
import com.cyhee.rabit.model.user.User;

public class ReportFactory {	
	public static Report generate(ContentType type, Long id, User reporter) {
		Report report = new Report();
		report.setTargetType(type);
		report.setTargetId(id);
		report.setReporter(reporter);
		return report;
	}
}
