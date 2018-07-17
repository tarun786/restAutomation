package com.rest.twiter.test;

import java.io.IOException;
import java.util.Properties;

import restProj.restAutomation.ResourceFinder;

public class AuthenticationResource {
	private String consumerKey;
	private String consumerSerertKey;
	private String accessTokenKey;
	private String accessTokenSecertKey;
public AuthenticationResource() throws IOException
{
	Properties prop = ResourceFinder.getRestProp();
	consumerKey = prop.getProperty("conKey");
	consumerSerertKey = prop.getProperty("conSecKey");
	accessTokenKey = prop.getProperty("accTokKey");
	accessTokenSecertKey = prop.getProperty("accTokSecKey");
}




public String getConsumerSerertKey() {
	return consumerSerertKey;
}
public void setConsumerSerertKey(String consumerSerertKey) {
	this.consumerSerertKey = consumerSerertKey;
}
public String getAccessTokenKey() {
	return accessTokenKey;
}
public void setAccessTokenKey(String accessTokenKey) {
	this.accessTokenKey = accessTokenKey;
}
public String getAccessTokenSecertKey() {
	return accessTokenSecertKey;
}
public void setAccessTokenSecertKey(String accessTokenSecertKey) {
	this.accessTokenSecertKey = accessTokenSecertKey;
}
public String getConsumerKey() {
	return consumerKey;
}
	
}
