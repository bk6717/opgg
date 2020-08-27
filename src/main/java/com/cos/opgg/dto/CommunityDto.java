package com.cos.opgg.dto;

import com.cos.opgg.model.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CommunityDto {
	
	private int type;
	
	private Post post;
	
}
