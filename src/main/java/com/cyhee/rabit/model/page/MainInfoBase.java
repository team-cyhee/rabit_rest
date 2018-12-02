package com.cyhee.rabit.model.page;

import com.cyhee.rabit.model.cmm.ContentType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainInfoBase {
	private Long order;
	private ContentType type;
	private Long id;
}
