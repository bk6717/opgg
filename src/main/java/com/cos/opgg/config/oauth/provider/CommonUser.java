package com.cos.opgg.config.oauth.provider;

import java.util.Map;

// 일반 로그인 유저

public class CommonUser implements OAuthUserInfo{

	private Map<String, Object> attribute;

	public CommonUser(Map<String, Object> attribute) {
		System.out.println("oauth.provider.CommonUser.java의 CommonUser에 왔습니다 attribute= "+attribute);
		this.attribute = attribute;
	}

	@Override
	public String getProviderId() {
		System.out.println("oauth.provider.CommonUser.java의 getProviderId에 왔습니다 ");
		return (String)attribute.get("email");
	}

	@Override
	public String getProvider() {
		System.out.println("oauth.provider.CommonUser.java의getProvider에 왔습니다 ");

		return "common";
	}

	@Override
	public String getEmail() {
		System.out.println("oauth.provider.CommonUser.java의getEmail에 왔습니다 ");

		return (String)attribute.get("email");
	}

	@Override
	public String getName() {
		System.out.println("oauth.provider.CommonUser.java의 getName에 왔습니다 ");

		return (String)attribute.get("username");
	}

	public String getPassword() {
		return (String)attribute.get("password");
	}

}
