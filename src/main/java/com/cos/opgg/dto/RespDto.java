package com.cos.opgg.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespDto<T> {
	
	private int statusCode;
	private String message;
	
	private T Data;
}
