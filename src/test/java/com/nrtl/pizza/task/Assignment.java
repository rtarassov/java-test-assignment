package com.nrtl.pizza.task;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Assignment {

	TASK_01(TaskDescription.TASK_DESCRIPTION_01, 5),
	TASK_02(TaskDescription.TASK_DESCRIPTION_02, 2),
	TASK_03(TaskDescription.TASK_DESCRIPTION_03, 3),
	TASK_04(TaskDescription.TASK_DESCRIPTION_04, 5),
	TASK_05(TaskDescription.TASK_DESCRIPTION_05,5),
	TASK_06(TaskDescription.TASK_DESCRIPTION_06, 7),
	TASK_07(TaskDescription.TASK_DESCRIPTION_07, 2),
	TASK_08(TaskDescription.TASK_DESCRIPTION_08, 2),
	TASK_09(TaskDescription.TASK_DESCRIPTION_09, 5),
	TASK_10(TaskDescription.TASK_DESCRIPTION_10, 2),
	TASK_11(TaskDescription.TASK_DESCRIPTION_11, 2);

	public static class TaskDescription {
		public static final String TASK_DESCRIPTION_01 = "Assignment 01 - Candidate unit testing capabilities";
		public static final String TASK_DESCRIPTION_02 = "Assignment 02 - Implement additional field in the response";
		public static final String TASK_DESCRIPTION_03 = "Assignment 03 - Implement filtering";
		public static final String TASK_DESCRIPTION_04 = "Assignment 04 - Implement an additional field for storing historic order price information";
		public static final String TASK_DESCRIPTION_05 = "Assignment 05 - Implement service for calculating average client expenses";
		public static final String TASK_DESCRIPTION_06 = "Assignment 06 - Implement service for calculating total pizza generated revenue and ordered ratio";
		public static final String TASK_DESCRIPTION_07 = "Assignment 07 - Implement that disabled user should not be able to access application";
		public static final String TASK_DESCRIPTION_08 = "Assignment 08 - Implement the correct security role configuration";
		public static final String TASK_DESCRIPTION_09 = "Assignment 09 - Implement weekly ordered pizza report";
		public static final String TASK_DESCRIPTION_10 = "Assignment 10 - Improve performance of user loading";
		public static final String TASK_DESCRIPTION_11 = "Assignment 11 - Error handling for non existing order id";
	}

	private final String description;

	private final int points;

}