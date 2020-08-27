package com.cos.opgg.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cos.opgg.api.apimodel.attr.rank.Entry;
import com.cos.opgg.util.AscRank;

public class Test {

//	public static void main(String[] args) {
//
//		List<Entry> list = new ArrayList<Entry>();
//		
//		list.add(Entry.builder().leaguePoints(446).build());
//		list.add(Entry.builder().leaguePoints(456).build());
//		list.add(Entry.builder().leaguePoints(123).build());
//		list.add(Entry.builder().leaguePoints(3).build());
//		list.add(Entry.builder().leaguePoints(77777).build());
//		list.add(Entry.builder().leaguePoints(1).build());
//		list.add(Entry.builder().leaguePoints(3344).build());
//		list.add(Entry.builder().leaguePoints(5).build());
//		list.add(Entry.builder().leaguePoints(22).build());
//		list.add(Entry.builder().leaguePoints(2266).build());
//		list.add(Entry.builder().leaguePoints(22).build());
//
//		
//		Collections.sort(list, new AscRank());
//		
//		System.out.println(list);
//		
//		
//	}
	
}


//public interface PostRepository extends JpaRepository<Post, Integer>{
//	   
//	   Post findById(int id);
//	   
//	   @Query(value = "select p.id as id, u.username as username, p.title as title,"+ 
//	   	"p.content as content, p.createDate as createDate, p.likeCount as likeCount,"+
//	   	"p.viewCount as viewCount "+
//	   	"from post p, user u "+
//	   	"where p.userId = u.id ", nativeQuery = true)
//	   List<PostUserDto> test();
//	   
//	   @Query(value = "SELECT * FROM post WHERE title LIKE %?1% OR content LIKE %?1%", nativeQuery = true)
//	   List<Post> search(String content);
//	   
//	}