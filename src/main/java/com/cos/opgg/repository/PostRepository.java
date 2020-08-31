package com.cos.opgg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.opgg.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

	Post findById(int id);

//	@Query(value = "select p.id as id, u.username as username, p.title as title, p.content as content, p.createDate as createDate, p.likeCount as likeCount, p.viewCount as viewCount from post p, user u where p.userId = u.id ", nativeQuery = true)
//	List<PostUserDto> test();

	@Query(value = "SELECT * FROM post WHERE title LIKE %?1% OR content LIKE %?1% ORDER BY createDate DESC" , nativeQuery = true)
	List<Post> findByContent(String content);

//	@Query(value = "SELECT * FROM post ORDER BY createDate DESC LIMIT 40", nativeQuery = true)
//	Page<Post>  postTest(Pageable pageable);

}
