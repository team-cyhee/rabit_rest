
package com.cyhee.rabit.goallog.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table
@Setter @Getter @ToString
public class GoalLog {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
}
