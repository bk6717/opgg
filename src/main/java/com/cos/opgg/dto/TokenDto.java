package com.cos.opgg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
	
	private int userId;
	private String nickname;
	private String jwtToken;

}
