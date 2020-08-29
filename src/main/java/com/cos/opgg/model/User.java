package com.cos.opgg.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "username", unique = true)
	private String username;
	
	@Column(name = "nickname", unique = true)
	private String nickname;
	
	@Column(name = "password")
    private String password;
	
	@Column(name = "email")
    private String email;
	
	@Column(name = "roles")
    private String roles;
	
	@Column(name = "providerId")
    private String providerId;
	
	@Column(name = "provider")
    private String provider;
    
    @CreationTimestamp
    @Column(name = "createDate")
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
