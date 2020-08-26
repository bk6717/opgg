package com.cos.opgg.dto;


import java.sql.Timestamp;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostUserDto {
	
	private String username;
	private int id;
	private int userId;// userId
	private String title;
	private String content;
	private Timestamp createDate;
	private int likeCount;
	private int viewCount;
}
