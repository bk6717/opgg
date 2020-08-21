package com.cos.opgg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.opgg.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	Post findById(int id);

}
