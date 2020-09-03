package com.cos.opgg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.opgg.model.Post;
import com.cos.opgg.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	
	 Reply findById(int id);
	 
	 void deleteByUserId(int id);
	
	 //게시글 검색기능
	 @Query(value =" select * from reply where reply like %?1%", nativeQuery = true)
	 List<Reply> findByReply(String reply);
	 
}
