
package com.cyhee.rabit.goallog.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class GoalLog {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
}
