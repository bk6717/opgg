package com.cos.opgg.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString(exclude = {"user", "replies"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotBlank
	@Column(name = "title")
	private String title;
	
	@NotBlank
	@Column(name = "content")
	private String content;
	
	@ColumnDefault("0")
	@Column(name = "likeCount")
	private int likeCount;
	
	@ColumnDefault("0")
	@Column(name = "viewCount")
	private int viewCount;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	@JsonIgnoreProperties({"password"})
	private User user;
	
	@OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({"post"}) // replies안의 post안가져오기
	@OrderBy("createDate DESC") // 댓글 최신글부터 가져오기
	private List<Reply> replies;
	
	@CreationTimestamp
	@Column(name = "createDate")
	private Timestamp createDate;
	
}

