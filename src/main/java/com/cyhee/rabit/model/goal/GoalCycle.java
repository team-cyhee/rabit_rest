package com.cyhee.rabit.model.goal;

/**
 * 목표를 달성하는 주기에 대한 enum
 * @author chy
 *
 */
public enum GoalCycle {
	// every day
	DAILY,	
	// per week
	W_ONCE,	W_TWICE, W_THREE, W_FOUR, W_FIVE, W_SIX,
	// day of week
	W_MON, W_TUE, W_WED, W_THUR, W_FRI, W_SAT, W_SUN,
	// per month
	M_ONCE,	M_TWICE, M_THREE, M_FOUR, M_FIVE, M_SIX,
	// day of month
	M_1ST, M_2ND, M_3RD, M_4TH, M_5TH, M_6TH, M_7TH, M_8TH, M_9TH, M_10TH, M_11TH,
	M_12TH, M_13TH, M_14TH, M_15TH, M_16TH, M_17TH, M_18TH, M_19TH, M_20TH,	M_21TH, 
	M_22TH, M_23TH, M_24TH, M_25TH, M_26TH, M_27TH, M_28TH, M_29TH, M_30TH, M_31TH,
	// per year
	Y_ONCE,	Y_TWICE, Y_THREE, Y_FOUR, Y_FIVE, Y_SIX, Y_SEVEN, Y_EIGHT, Y_NINE, Y_TEN,
	// month of year
	Y_JAN, Y_FEB, Y_MAR, Y_APR, Y_MAY, Y_JUNE, Y_JULY, Y_AUG, Y_SEP, Y_OCT, Y_NOV, Y_DEC,
}
