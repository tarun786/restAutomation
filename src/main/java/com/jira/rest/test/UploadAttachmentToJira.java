package com.jira.rest.test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.testng.annotations.Test;

import restProj.restAutomation.ResourceFinder;

@SuppressWarnings("deprecation")
public class UploadAttachmentToJira {
    Properties prop;
	@Test
	public void callUpload() throws IOException
	{
		System.out.println(uploadAttachment());
	}
	
	public boolean uploadAttachment() throws IOException
	{
		String user = "tarunjaiswal92";
		String pass= "xxxx";
		String sessionID = ResourceFinder.getSession();
		//create the bug
		String issueID = ResourceFinder.getIssueID();
		String auth = new String(org.apache.commons.codec.binary.Base64.encodeBase64((user+":"+pass).getBytes()));
	   System.out.println("encode auth string "+auth);
	  
		HttpClient client = new DefaultHttpClient();
		System.out.println(ResourceFinder.getRestProp().getProperty("JiraHOST"));
	    HttpPost httpPost = new HttpPost(ResourceFinder.getRestProp().getProperty("JiraHOST")+"/rest/api/2/issue/"+issueID+"/attachments");
	    httpPost.setHeader("X-Atlassian-Token", "nocheck");
	    httpPost.setHeader("Authorization", "Basic "+auth);
	    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	    File fileUpload = new File("C:\\Users\\tjaiswal\\workspace\\restAutomation\\files\\postdata.xml");
	    FileBody fileBody = new FileBody(fileUpload, "application/octet-stream");
	    multipartEntity.addPart("file", fileBody);
	    httpPost.setEntity(multipartEntity);
	    HttpResponse httpResponse = null;
	    try{
	    	httpResponse = client.execute(httpPost);
	    }
	    catch (ClientProtocolException e) {
	        return false;
	    } catch (IOException e) {
	        return false;
	    }
	    HttpEntity httpEntity = httpResponse.getEntity();
	    System.out.println("responce "+httpResponse.getStatusLine().getStatusCode());
	    if(httpResponse.getStatusLine().getStatusCode() == 200)
	        return true;
	    else
	        return false;

	}
}
