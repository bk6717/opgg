package com.cos.opgg.config.oauth.provider;

import java.util.Map;

public class KakaoUser implements OAuthUserInfo{

	private Map<String, Object> attribute;

	public KakaoUser(Map<String, Object> attribute) {
		System.out.println("oauth.provider.GoogleUser.java의 GoogleUser에 왔습니다 attribute= "+attribute);
		this.attribute = attribute;
	}

	@Override
	public String getProviderId() {
		System.out.println("oauth.provider.GoogleUser.java의 getProviderId에 왔습니다 ");
		return (String)attribute.get("kakaoId");
	}

	@Override
	public String getProvider() {
		System.out.println("oauth.provider.GoogleUser.java의getProvider에 왔습니다 ");

		return "kakao";
	}

	@Override
	public String getEmail() {
		System.out.println("oauth.provider.GoogleUser.java의getEmail에 왔습니다 ");

		return (String)attribute.get("email");
	}

	@Override
	public String getName() {
		System.out.println("oauth.provider.GoogleUser.java의 getName에 왔습니다 ");

		return (String)attribute.get("name");
	}

}
