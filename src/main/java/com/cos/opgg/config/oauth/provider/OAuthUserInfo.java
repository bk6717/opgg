package com.cos.opgg.config.oauth.provider;

public interface OAuthUserInfo {
	String getProviderId();
	String getProvider();
	String getEmail();
	String getName();
}
