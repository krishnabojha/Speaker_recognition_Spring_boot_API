package com.example.demo;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class CallPrediction {

	public String makePrediction(String audiopath, String perform) throws IOException {
		
//		post request to send audio file to the flask API
		File file = new File(audiopath);
		HttpPost post = new HttpPost("http://127.0.0.1:5000/"+perform);
		FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addPart("file", fileBody);
		HttpEntity entity = builder.build();

		CloseableHttpClient client = HttpClients.createDefault();
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		String responseString = new BasicResponseHandler().handleResponse(response);
		System.out.println("returned body respones form flask API: "+responseString);
		
		return responseString;
	}
}
