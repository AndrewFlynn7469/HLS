package edu.psgv.sweng861.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebRequestUtil {
	//Constructs HttpRequest for URLs (although ideally we could stream these right from URL.openStream())
	private Logger logger = LogManager.getFormatterLogger(getClass());
	
	public ArrayList<String> getPageContents(URL url) throws IOException, UnknownHostException{
		//This should probably be an implementation of this util but will include it here to avoid HttpRequest dependency in application layer
		HttpURLConnection connection = setRequest(url, "GET");
		return readResponseStream(connection);
	}
	
	public HttpURLConnection setRequest(URL url, String method) throws IOException, UnknownHostException{
		//This sets up an HTTP request for the given URL and method
		logger.info("Sending request for URL " + url);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(method);
		return connection;
	}
	
	public ArrayList<String> readResponseStream(HttpURLConnection connection) throws IOException{
		//This reads an input stream from an Http connection and returns the contents
		ArrayList<String> output = new ArrayList<String>();
		InputStream is = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line;
		while((line = reader.readLine()) != null){
			output.add(line);
		}
		return output;
	}
}
