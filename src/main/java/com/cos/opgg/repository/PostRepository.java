package com.cos.opgg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.opgg.model.Post;
import com.cos.opgg.model.User;

public interface PostRepository extends JpaRepository<Post, Integer> {

	Post findById(int id);
	
	void deleteByUserId(int id);

	@Query(value = "SELECT * FROM post WHERE title LIKE %?1% OR content LIKE %?1% ORDER BY createDate DESC" , nativeQuery = true)
	List<Post> findByContent(String content);

}
