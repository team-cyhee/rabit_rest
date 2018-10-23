package com.cyhee.rabit.model.goal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 목표를 달성하는 주기에 대한 enum
 * @author chy
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GoalCycle {
	private GoalUnit unit;
	private Integer times;
}
