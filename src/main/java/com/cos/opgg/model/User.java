package com.cos.opgg.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;  
    private String password;
    private String email;
    private String roles;
    private String providerId;
    private String provider;
    private Timestamp createDate;
    
	
//    public List<String> getRoleList(){
//    	System.out.println("model.User의 getRoleList()에 왔습니다");
//        if(this.roles.length() > 0){
//        	System.out.println("model.User의 getRoleList()의 if문에 왔습니다");
//            return Arrays.asList(this.roles.split(","));
//        }
//
//        return new ArrayList<>();
//    }
}
