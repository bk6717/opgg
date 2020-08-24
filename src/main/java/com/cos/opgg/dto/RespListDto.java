package com.cos.opgg.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespListDto<T> {
	private int statusCode;
	private String message;
	private List<T> data;
}
