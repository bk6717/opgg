package com.cos.opgg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.opgg.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	
	Reply findById(int id);
	
}
