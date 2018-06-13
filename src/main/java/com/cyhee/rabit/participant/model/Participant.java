
package com.cyhee.rabit.participant.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Setter @Getter @ToString
public class Participant {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
}
