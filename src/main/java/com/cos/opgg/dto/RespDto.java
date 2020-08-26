package com.cos.opgg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespDto<T> {
	
	private int statusCode;
	private String message;
	private T data;
}
